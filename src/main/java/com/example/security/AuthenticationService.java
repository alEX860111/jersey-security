package com.example.security;

import javax.inject.Inject;

import com.example.domain.AuthenticationRequest;
import com.example.domain.AuthenticationToken;
import com.example.security.token.ITokenService;

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
		User user = userService.getUser(request);
		return tokenService.createToken(user);
	}

}
