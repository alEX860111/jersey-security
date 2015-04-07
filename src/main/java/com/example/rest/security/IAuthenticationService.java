package com.example.rest.security;

import com.example.rest.domain.AuthenticationRequest;
import com.example.rest.domain.AuthenticationToken;

public interface IAuthenticationService {

	public AuthenticationToken authenticate(AuthenticationRequest request);

}
