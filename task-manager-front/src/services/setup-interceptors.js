import axiosInstance from './api-config';
import TokenService from "./token-service";
import router from '../router';
import Vue from 'vue';
import {i18n} from "../i18n";

const setup = (store) => {
    axiosInstance.interceptors.request.use(
        (config) => {
            const token = TokenService.getLocalAccessToken();
            if (token !== null) {
                config.headers["Authorization"] = 'Bearer ' + token;
            }
            return config;
        },
        (error) => {
            return Promise.reject(error);
        }
    );

    axiosInstance.interceptors.response.use(
        (res) => {
            return res;
        },
        async (err) => {

            if (err.toString() !== 'Error: Network Error') {
                const originalConfig = err.config;
                console.log(err.response)
                if (originalConfig.data !== undefined) {
                    Vue.toasted.show(originalConfig.data.message);
                }else{
                    Vue.toasted.show(err.response.data.error);
                }
                if (err.response.status === 406) {
                    Vue.toasted.show(err.response.data.message);
                    store.dispatch('auth/logout');
                    await router.push('/' + i18n.locale + '/login');
                }

                if (err.response.status === 401 && store.state.auth.user !== null) {
                    if (store.state.auth.user.accessToken !== null) {
                        if (originalConfig.url !== "/v1/login" && err.response) {
                            // Access Token was expired
                            if (err.response.status === 401 && !originalConfig._retry) {
                                originalConfig._retry = true;
                                try {
                                    const rs = await axiosInstance.post("/v1/refreshToken", {
                                        refreshToken: TokenService.getLocalRefreshToken(),
                                    });
                                    const {accessToken} = rs.data;
                                    store.dispatch('auth/refreshToken', accessToken);
                                    TokenService.updateLocalAccessToken(accessToken);
                                    return axiosInstance(originalConfig);
                                } catch (_error) {
                                    return Promise.reject(_error);
                                }
                            }
                        }
                    } else {
                        store.dispatch('auth/logout');
                        await router.push('/' + i18n.locale + '/401');
                    }
                } else if (err.response.status === 401 && store.state.auth.user === null) {
                    await router.push('/' + i18n.locale + '/401');
                }
            }
            return Promise.reject(err);
        }
    );
};

export default setup;
