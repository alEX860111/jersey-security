package com.example.security.token;

import com.example.domain.AuthenticationToken;
import com.example.security.User;

public interface ITokenService {

	public AuthenticationToken createToken(User user);

}
