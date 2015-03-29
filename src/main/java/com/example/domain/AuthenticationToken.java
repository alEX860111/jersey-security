package com.example.domain;

public final class AuthenticationToken {

	private String token;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public static AuthenticationToken create(String jwt) {
		AuthenticationToken token = new AuthenticationToken();
		token.setToken(jwt);
		return token;
	}

}
