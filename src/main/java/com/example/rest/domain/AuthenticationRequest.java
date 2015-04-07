package com.example.rest.domain;

import javax.validation.constraints.NotNull;

public final class AuthenticationRequest {

	@NotNull
	public String username;

	@NotNull
	public String password;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
