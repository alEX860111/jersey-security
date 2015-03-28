package com.example.security;

import com.example.domain.AuthenticationRequest;
import com.example.domain.AuthenticationResponse;

public interface IAuthenticationService {

	public AuthenticationResponse authenticate(AuthenticationRequest request);

}
