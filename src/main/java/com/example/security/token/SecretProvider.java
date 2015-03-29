package com.example.security.token;

import java.security.SecureRandom;

import javax.inject.Provider;

final class SecretProvider implements Provider<byte[]> {

	private final byte[] secret;

	public SecretProvider() {
		// Generate random 256-bit (32-byte) shared secret
		final SecureRandom random = new SecureRandom();
		secret = new byte[32];
		random.nextBytes(secret);
	}

	@Override
	public byte[] get() {
		return secret;
	}

}
