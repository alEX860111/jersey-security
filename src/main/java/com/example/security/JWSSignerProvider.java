package com.example.security;

import javax.inject.Inject;
import javax.inject.Provider;

import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.crypto.MACSigner;

final class JWSSignerProvider implements Provider<JWSSigner> {

    private final JWSSigner signer;

    @Inject
    public JWSSignerProvider(@Secret byte[] secret) {
        this.signer = new MACSigner(secret);
    }

    @Override
    public JWSSigner get() {
        return signer;
    }

}
