package com.example.security;

import javax.inject.Singleton;

import com.example.security.ITokenService;
import com.example.security.JWSSignerProvider;
import com.example.security.SecretProvider;
import com.example.security.TokenService;
import com.google.inject.AbstractModule;
import com.nimbusds.jose.JWSSigner;

public final class SecurityModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(byte[].class).annotatedWith(Secret.class).toProvider(SecretProvider.class).in(Singleton.class);
        bind(JWSSigner.class).toProvider(JWSSignerProvider.class).in(Singleton.class);

        bind(ITokenService.class).to(TokenService.class);
    }

}
