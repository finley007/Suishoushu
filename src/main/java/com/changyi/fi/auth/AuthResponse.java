package com.changyi.fi.auth;

/**
 * Created by finley on 1/25/17.
 */
public class AuthResponse {

    public AuthResponse(String token) {
        this.token = token;
    }

    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}
