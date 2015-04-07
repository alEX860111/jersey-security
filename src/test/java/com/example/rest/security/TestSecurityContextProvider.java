package com.example.rest.security;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.net.URI;
import java.text.ParseException;
import java.time.LocalDateTime;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.example.rest.security.AuthenticationException;
import com.example.rest.security.SecurityContextProvider;
import com.example.util.DateConverter;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

@RunWith(MockitoJUnitRunner.class)
public class TestSecurityContextProvider {

	@Mock
	private SignedJWT jwt;

	@Mock
	private UriInfo uriInfo;

	private JWTClaimsSet claims;

	private SecurityContextProvider providerSUT;

	@Before
	public void setUp() {
		claims = new JWTClaimsSet();

		URI uri = URI.create("http://localhost:8080/products");
		when(uriInfo.getRequestUri()).thenReturn(uri);

		providerSUT = new SecurityContextProvider();
	}

	@Test
	public void testGet() throws ParseException {
		claims.setExpirationTime(DateConverter.getDate(LocalDateTime.now().plusHours(24)));

		when(jwt.getJWTClaimsSet()).thenReturn(claims);
		SecurityContext ctx = providerSUT.get(jwt, uriInfo);
		assertNotNull(ctx);
		verify(jwt).getJWTClaimsSet();
	}

	@Test(expected = AuthenticationException.class)
	public void testGet_tokenExpired() throws ParseException {
		claims.setExpirationTime(DateConverter.getDate(LocalDateTime.now().minusHours(24)));

		when(jwt.getJWTClaimsSet()).thenReturn(claims);
		providerSUT.get(jwt, uriInfo);
	}

	@Test(expected = BadRequestException.class)
	public void testGet_exception() throws ParseException {
		when(jwt.getJWTClaimsSet()).thenThrow(new ParseException(null, 0));
		providerSUT.get(jwt, uriInfo);
	}

}
