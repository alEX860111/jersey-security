package com.example.domain;

public class Token {

    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
    
    public static Token create(String jwt) {
        Token token = new Token();
        token.setToken(jwt);
        return token;
    }

}
