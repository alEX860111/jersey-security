package com.example.rest.security;

import com.google.inject.AbstractModule;

public final class SecurityModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(IAuthenticationService.class).to(AuthenticationService.class);
		bind(IAuthenticationTokenVerifier.class).to(AuthenticationTokenVerifier.class);
		bind(ISecurityContextProvider.class).to(SecurityContextProvider.class);
	}

}
