import TokenService from '../services/token-service';
import api from './api-config';

class AuthService {
    login(user) {
        return api.post('/user-manager/api/v1/users/login', {
            username: user.username,
            password: user.password
        }).then(response => {
            if (response.data.accessToken) {
                TokenService.setUser(response.data);
            }
            return response.data;
        });
    }

    logout() {
        TokenService.removeUser();
    }

    userLogout() {
        return api.post('/user-manager/api/v1/users/logout');
    }
}

export default new AuthService();
