package com.example.security;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.nimbusds.jwt.SignedJWT;

@RunWith(MockitoJUnitRunner.class)
public class TestAuthenticationFilter {

	@Mock
	private IAuthenticationTokenVerifier verifier;

	@Mock
	private SignedJWT jwt;

	@Mock
	private ISecurityContextProvider provider;

	@Mock
	private SecurityContext context;

	@Mock
	private ContainerRequestContext request;

	@Mock
	private UriInfo uriInfo;

	private MultivaluedMap<String, String> headers;

	@InjectMocks
	private AuthenticationFilter filterSUT;

	@Before
	public void setUp() {
		headers = new MultivaluedHashMap<>();
		when(request.getHeaders()).thenReturn(headers);
		when(request.getUriInfo()).thenReturn(uriInfo);
	}

	@Test
	public void testFilter_token() throws IOException {
		String token = "token";
		headers.add(HttpHeaders.AUTHORIZATION, token);
		when(verifier.verify(token)).thenReturn(jwt);
		when(provider.get(jwt, uriInfo)).thenReturn(context);

		filterSUT.filter(request);
		verify(verifier).verify(token);
		verify(provider).get(jwt, uriInfo);
		verify(request).setSecurityContext(context);
	}

	@Test
	public void testFilter_noToken() throws IOException {
		filterSUT.filter(request);
		verifyZeroInteractions(verifier);
		verifyZeroInteractions(provider);
	}

}
