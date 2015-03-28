package com.example.security;

import com.example.domain.AuthenticationRequest;

final class UserService implements IUserService {

	@Override
	public User getUser(AuthenticationRequest request) {
		if (!"joe".equals(request.getUsername()) || !"pw".equals(request.getPassword())) {
			throw new AuthenticationException();
		}
		
		User user = new User();
		user.setUsername(request.getUsername());
		user.setRole(Role.USER);
		return user;
	}

}
