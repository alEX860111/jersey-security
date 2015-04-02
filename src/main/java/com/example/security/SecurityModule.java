package com.example.security;

import javax.inject.Singleton;

import com.google.inject.AbstractModule;

public final class SecurityModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(IUserService.class).to(UserService.class).in(Singleton.class);;
		bind(IAuthenticationService.class).to(AuthenticationService.class);
		bind(IAuthenticationTokenVerifier.class).to(AuthenticationTokenVerifier.class);
		bind(ISecurityContextProvider.class).to(SecurityContextProvider.class);
	}

}
