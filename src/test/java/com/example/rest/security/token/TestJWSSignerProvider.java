package com.example.rest.security.token;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;

import org.junit.Before;
import org.junit.Test;

import com.example.rest.security.token.JWSSignerProvider;
import com.nimbusds.jose.JWSSigner;

public class TestJWSSignerProvider {

	private static final byte[] SECRET = new byte[] { -21, -67, -8, -17, 25, 94, -73, 4, 120, 5, 84, -71, -15, -80, 52,
			-115, 112, 109, 42, -79, -95, 93, 79, 38, 108, -96, -91, 111, 44, 43, 10, 104 };

	private JWSSignerProvider providerSUT;

	@Before
	public void setUp() {
		providerSUT = new JWSSignerProvider(SECRET);
	}

	@Test
	public void testGet() {
		JWSSigner signer1 = providerSUT.get();
		JWSSigner signer2 = providerSUT.get();
		assertNotNull(signer1);
		assertNotNull(signer2);
		assertSame(signer1, signer2);
	}

}
