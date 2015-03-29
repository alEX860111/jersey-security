package com.example.security;

import java.io.IOException;

import javax.annotation.Priority;
import javax.inject.Inject;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;

import org.apache.commons.lang3.StringUtils;

import com.nimbusds.jwt.SignedJWT;

@Priority(Priorities.AUTHENTICATION)
public final class AuthenticationFilter implements ContainerRequestFilter {

	private final IAuthenticationTokenVerifier verifier;

	private final ISecurityContextProvider provider;

	@Inject
	public AuthenticationFilter(IAuthenticationTokenVerifier verifier, ISecurityContextProvider provider) {
		this.verifier = verifier;
		this.provider = provider;
	}

	@Override
	public void filter(ContainerRequestContext request) throws IOException {
		final String token = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
		if (StringUtils.isNotBlank(token)) {
			final SignedJWT jwt = verifier.verify(token);
			request.setSecurityContext(provider.get(jwt, request.getUriInfo()));
		}
	}

}
