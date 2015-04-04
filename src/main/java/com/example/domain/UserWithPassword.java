package com.example.domain;

import javax.validation.constraints.NotNull;

public final class UserWithPassword extends User {

	@NotNull
	private String password;

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
