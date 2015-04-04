package com.example.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

import com.example.domain.UserWithPassword;
import com.example.security.Role;

final class UserService implements IUserService {

	private final Map<String, UserWithPassword> users = new ConcurrentHashMap<>();

	public UserService() {
		UserWithPassword admin = new UserWithPassword();
		admin.setUsername("admin");
		admin.setPassword("password");
		admin.setRole(Role.ADMIN);
		createUser(admin);
	}

	@Override
	public List<UserWithPassword> getAllUsers() {
		return Collections.unmodifiableList(new ArrayList<>(users.values()));
	}

	@Override
	public UserWithPassword createUser(UserWithPassword user) {
		return Objects.isNull(users.putIfAbsent(user.getUsername(), user)) ? user : null;
	}

	@Override
	public UserWithPassword getUser(String username) {
		return users.get(username);
	}

	@Override
	public UserWithPassword updateUser(UserWithPassword user) {
		return Objects.isNull(users.replace(user.getUsername(), user)) ? null : user;
	}

	@Override
	public UserWithPassword deleteUser(String username) {
		return users.remove(username);
	}

}
