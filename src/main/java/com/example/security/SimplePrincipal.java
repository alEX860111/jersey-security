package com.example.security;

import java.security.Principal;

final class SimplePrincipal implements Principal {

	private final String name;

	private SimplePrincipal(String name) {
		this.name = name;
	}

	@Override
	public String getName() {
		return name;
	}

	public static Principal create(String name) {
		return new SimplePrincipal(name);
	}

}