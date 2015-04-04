package com.example.security.token;

import com.example.domain.AuthenticationToken;
import com.example.domain.UserWithPassword;

public interface ITokenService {

	public AuthenticationToken createToken(UserWithPassword user);

}
