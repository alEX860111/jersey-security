package com.example.rest.security.token;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.example.rest.security.token.SecretProvider;

public class TestSecretProvider {

	private SecretProvider providerSUT;

	@Before
	public void setUp() {
		providerSUT = new SecretProvider();
	}

	@Test
	public void testGetReturns256bitSecret() {
		byte[] secret = providerSUT.get();
		assertNotNull(secret);
		assertEquals(32, secret.length);
	}

	@Test
	public void testGetReturnsSameSecret() {
		byte[] secret1 = providerSUT.get();
		byte[] secret2 = providerSUT.get();
		assertSame(secret1, secret2);
	}

}
