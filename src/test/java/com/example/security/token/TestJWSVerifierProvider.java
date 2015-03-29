package com.example.security.token;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;

import org.junit.Before;
import org.junit.Test;

import com.nimbusds.jose.JWSVerifier;

public class TestJWSVerifierProvider {

	private static final byte[] SECRET = new byte[] { -21, -67, -8, -17, 25, 94, -73, 4, 120, 5, 84, -71, -15, -80, 52,
			-115, 112, 109, 42, -79, -95, 93, 79, 38, 108, -96, -91, 111, 44, 43, 10, 104 };

	private JWSVerifierProvider providerSUT;

	@Before
	public void setUp() {
		providerSUT = new JWSVerifierProvider(SECRET);
	}

	@Test
	public void testGet() {
		JWSVerifier verifier1 = providerSUT.get();
		JWSVerifier verifier2 = providerSUT.get();
		assertNotNull(verifier1);
		assertNotNull(verifier2);
		assertSame(verifier1, verifier2);
	}

}
