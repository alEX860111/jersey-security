package com.example.security;

import com.example.domain.AuthenticationRequest;
import com.example.domain.AuthenticationToken;

public interface IAuthenticationService {

	public AuthenticationToken authenticate(AuthenticationRequest request);

}
