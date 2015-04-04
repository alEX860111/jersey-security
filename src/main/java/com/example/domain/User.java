package com.example.domain;

import javax.validation.constraints.NotNull;

import com.example.security.Role;

public class User {

	@NotNull
	private String username;

	@NotNull
	private Role role;

	public User() {}

	public User(UserWithPassword user) {
		username = user.getUsername();
		role = user.getRole();
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

}
