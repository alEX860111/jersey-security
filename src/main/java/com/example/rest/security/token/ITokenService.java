package com.example.rest.security.token;

import com.example.rest.domain.AuthenticationToken;
import com.example.rest.domain.User;

public interface ITokenService {

	public AuthenticationToken createToken(User user);

}
