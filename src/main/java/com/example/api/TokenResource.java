package com.example.api;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.example.domain.AuthenticationRequest;
import com.example.domain.AuthenticationToken;
import com.example.security.IAuthenticationService;

@Path("token")
public final class TokenResource {

	private final IAuthenticationService service;

	@Inject
	public TokenResource(IAuthenticationService service) {
		this.service = service;
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public AuthenticationToken get(AuthenticationRequest request) {
		return service.authenticate(request);
	}

}