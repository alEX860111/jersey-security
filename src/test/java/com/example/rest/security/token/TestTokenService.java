package com.example.rest.security.token;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.text.ParseException;
import java.time.LocalDateTime;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.example.rest.domain.AuthenticationToken;
import com.example.rest.domain.Role;
import com.example.rest.domain.User;
import com.example.util.DateConverter;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jwt.ReadOnlyJWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

public class TestTokenService {

	private static final byte[] SECRET = new SecretProvider().get();

	private User user;

	private TokenService serviceSUT;

	@Before
	public void setUp() {
		user = new User();
		user.setUsername("joe");
		user.setRole(Role.USER);

		final JWSSigner signer = new JWSSignerProvider(SECRET).get();
		serviceSUT = new TokenService(signer);
	}

	@Test(expected = IllegalStateException.class)
	public void testCreateToken_tokenCannotBeSigned() {
		serviceSUT = new TokenService(Mockito.mock(JWSSigner.class));
		serviceSUT.createToken(user);
	}

	@Test
	public void testCreateToken_verifyToken() throws ParseException, JOSEException {
		AuthenticationToken token = serviceSUT.createToken(user);
		SignedJWT jwt = SignedJWT.parse(token.getToken());

		JWSVerifier verifier = new JWSVerifierProvider(SECRET).get();
		assertTrue(jwt.verify(verifier));
	}

	@Test
	public void testCreateToken_claims() throws ParseException {
		AuthenticationToken token = serviceSUT.createToken(user);
		SignedJWT jwt = SignedJWT.parse(token.getToken());

		final ReadOnlyJWTClaimsSet claims = jwt.getJWTClaimsSet();
		assertEquals(user.getUsername(), claims.getSubject());
		assertEquals(user.getRole().name(), claims.getCustomClaim(Role.class.getSimpleName().toLowerCase()));
		assertEquals(1, claims.getCustomClaims().size());
		assertEquals("http://localhost:8080", claims.getIssuer());

		LocalDateTime issueTime = DateConverter.getDateTime(claims.getIssueTime());
		LocalDateTime expireTime = DateConverter.getDateTime(claims.getExpirationTime());
		assertEquals(expireTime, issueTime.plusHours(24));
	}

}
