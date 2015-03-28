package com.example.security;

import com.example.domain.AuthenticationRequest;

public interface IUserService {

	public User getUser(AuthenticationRequest request);

}
