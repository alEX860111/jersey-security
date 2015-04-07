package com.example.rest.security.token;

import javax.inject.Inject;
import javax.inject.Provider;

import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.MACVerifier;

final class JWSVerifierProvider implements Provider<JWSVerifier> {

	private final JWSVerifier verifier;

	@Inject
	public JWSVerifierProvider(@Secret byte[] secret) {
		verifier = new MACVerifier(secret);
	}

	@Override
	public JWSVerifier get() {
		return verifier;
	}

}
