package com.zerodefects.usermanager;

import java.util.List;

public class TokenInfo {

    private String accessToken;
    private String refreshToken;
    private List<String> roles;

    public TokenInfo(String accessToken, String refreshToken, List<String> roles) {

        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.roles = roles;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
}
