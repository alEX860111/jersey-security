package com.example.security;

import java.util.Objects;

import javax.inject.Inject;
import javax.ws.rs.core.Response.Status;

import com.example.domain.AuthenticationRequest;
import com.example.domain.AuthenticationToken;
import com.example.domain.User;
import com.example.security.token.ITokenService;
import com.example.service.IUserService;

final class AuthenticationService implements IAuthenticationService {

	private final ITokenService tokenService;

	private final IUserService userService;

	@Inject
	public AuthenticationService(ITokenService service, IUserService userService) {
		this.tokenService = service;
		this.userService = userService;
	}

	@Override
	public AuthenticationToken authenticate(AuthenticationRequest request) {
		User user = userService.getUser(request.getUsername());
		if (Objects.isNull(user) || !user.getPassword().equals(request.getPassword())) {
			throw new AuthenticationException(Status.UNAUTHORIZED, "Wrong username and/or password.");
		}
		return tokenService.createToken(user);
	}

}
