package com.example.domain;

public final class AuthenticationResponse {

    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
    
    public static AuthenticationResponse create(String jwt) {
        AuthenticationResponse token = new AuthenticationResponse();
        token.setToken(jwt);
        return token;
    }

}
