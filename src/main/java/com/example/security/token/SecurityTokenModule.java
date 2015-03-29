package com.example.security.token;

import javax.inject.Singleton;

import com.google.inject.AbstractModule;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.JWSVerifier;

public final class SecurityTokenModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(byte[].class).annotatedWith(Secret.class).toProvider(SecretProvider.class).in(Singleton.class);
		bind(JWSSigner.class).toProvider(JWSSignerProvider.class).in(Singleton.class);
		bind(JWSVerifier.class).toProvider(JWSVerifierProvider.class).in(Singleton.class);
		bind(ITokenService.class).to(TokenService.class);
	}

}
