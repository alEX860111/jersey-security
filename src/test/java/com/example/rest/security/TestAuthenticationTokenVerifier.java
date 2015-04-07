package com.example.rest.security;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.verifyStatic;
import static org.powermock.api.mockito.PowerMockito.when;

import java.text.ParseException;

import javax.ws.rs.BadRequestException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.example.rest.security.AuthenticationException;
import com.example.rest.security.AuthenticationTokenVerifier;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jwt.SignedJWT;

@RunWith(PowerMockRunner.class)
@PrepareForTest(SignedJWT.class)
public class TestAuthenticationTokenVerifier {

	private static final String TOKEN = "token";

	@Mock
	private SignedJWT jwt;

	@Mock
	private JWSVerifier jwsVerifier;

	@InjectMocks
	private AuthenticationTokenVerifier verifierSUT;

	@Before
	public void setUp() {
		mockStatic(SignedJWT.class);
	}

	@Test
	public void testVerify() throws Exception {
		when(SignedJWT.parse(TOKEN)).thenReturn(jwt);
		when(jwt.verify(jwsVerifier)).thenReturn(true);

		SignedJWT verifiedJwt = verifierSUT.verify(TOKEN);
		assertEquals(jwt, verifiedJwt);

		verifyStatic();
		SignedJWT.parse(TOKEN);
		verify(jwt).verify(jwsVerifier);
	}

	@Test(expected = AuthenticationException.class)
	public void testVerify_failure() throws Exception {
		when(SignedJWT.parse(TOKEN)).thenReturn(jwt);
		when(jwt.verify(jwsVerifier)).thenReturn(false);
		verifierSUT.verify(TOKEN);
	}

	@Test(expected = BadRequestException.class)
	public void testVerify_parseException() throws Exception {
		when(SignedJWT.parse(TOKEN)).thenThrow(new ParseException(null, 0));
		verifierSUT.verify(TOKEN);
	}

	@Test(expected = BadRequestException.class)
	public void testVerify_joseException() throws Exception {
		when(SignedJWT.parse(TOKEN)).thenReturn(jwt);
		when(jwt.verify(jwsVerifier)).thenThrow(new JOSEException(null));
		verifierSUT.verify(TOKEN);
	}

}
