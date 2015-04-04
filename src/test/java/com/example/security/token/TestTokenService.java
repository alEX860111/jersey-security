package com.example.security.token;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.text.ParseException;
import java.time.LocalDateTime;

import org.junit.Before;
import org.junit.Test;

import com.example.domain.AuthenticationToken;
import com.example.domain.UserWithPassword;
import com.example.security.Role;
import com.example.security.token.JWSSignerProvider;
import com.example.security.token.JWSVerifierProvider;
import com.example.security.token.SecretProvider;
import com.example.security.token.TokenService;
import com.example.util.DateConverter;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jwt.ReadOnlyJWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

public class TestTokenService {

	private static final String USERNAME = "joe";

	private static final Role ROLE = Role.USER;

	private static final byte[] SECRET = new SecretProvider().get();

	private UserWithPassword user;

	private TokenService serviceSUT;

	@Before
	public void setUp() {
		user = new UserWithPassword();
		user.setUsername(USERNAME);
		user.setRole(ROLE);

		final JWSSigner signer = new JWSSignerProvider(SECRET).get();
		serviceSUT = new TokenService(signer);
	}

	@Test
	public void testVerification() throws ParseException, JOSEException {
		AuthenticationToken token = serviceSUT.createToken(user);
		SignedJWT jwt = SignedJWT.parse(token.getToken());

		JWSVerifier verifier = new JWSVerifierProvider(SECRET).get();
		assertTrue(jwt.verify(verifier));
	}

	@Test
	public void testClaims() throws ParseException {
		AuthenticationToken token = serviceSUT.createToken(user);
		SignedJWT jwt = SignedJWT.parse(token.getToken());

		final ReadOnlyJWTClaimsSet claims = jwt.getJWTClaimsSet();
		assertEquals(USERNAME, claims.getSubject());
		assertEquals(ROLE.name(), claims.getCustomClaim(Role.class.getCanonicalName()));
		assertEquals("https://example.com", claims.getIssuer());

		LocalDateTime issueTime = DateConverter.getDateTime(claims.getIssueTime());
		LocalDateTime expireTime = DateConverter.getDateTime(claims.getExpirationTime());
		assertEquals(expireTime, issueTime.plusHours(24));
	}

}
