package com.example.rest.api;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.example.rest.domain.AuthenticationRequest;
import com.example.rest.domain.AuthenticationToken;
import com.example.rest.security.IAuthenticationService;

@Path("token")
public final class TokenResource {

	private final IAuthenticationService service;

	@Inject
	public TokenResource(IAuthenticationService service) {
		this.service = service;
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public AuthenticationToken get(@Valid AuthenticationRequest request) {
		return service.authenticate(request);
	}

}