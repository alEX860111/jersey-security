package com.example.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

import com.example.rest.domain.Role;
import com.example.rest.domain.User;

final class UserService implements IUserService {

	private final Map<String, User> users = new ConcurrentHashMap<>();

	public UserService() {
		User admin = new User();
		admin.setUsername("admin");
		admin.setPassword("password");
		admin.setRole(Role.ADMIN);
		createUser(admin);
	}

	@Override
	public List<User> getAllUsers() {
		return Collections.unmodifiableList(new ArrayList<>(users.values()));
	}

	@Override
	public User createUser(User user) {
		return Objects.isNull(users.putIfAbsent(user.getUsername(), user)) ? user : null;
	}

	@Override
	public User getUser(String username) {
		return users.get(username);
	}

	@Override
	public User updateUser(User user) {
		return Objects.isNull(users.replace(user.getUsername(), user)) ? null : user;
	}

	@Override
	public User deleteUser(String username) {
		return users.remove(username);
	}

}
