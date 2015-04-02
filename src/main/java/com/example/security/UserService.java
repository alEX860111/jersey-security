package com.example.security;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

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
	public boolean createUser(User user) {
		return Objects.isNull(users.putIfAbsent(user.getUsername(), user)) ? true : false;
	}

	@Override
	public User getUser(String username) {
		return users.get(username);
	}

	@Override
	public boolean updateUser(User user) {
		return Objects.isNull(users.replace(user.getUsername(), user)) ? false : true;
	}

	@Override
	public boolean deleteUser(String username) {
		return Objects.isNull(users.remove(username)) ? false : true;
	}

}
