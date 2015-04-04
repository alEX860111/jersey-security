package com.example.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import com.example.domain.UserWithPassword;
import com.example.domain.User;

final class RestUserService implements IRestUserService {

	private final IUserService service;

	@Inject
	public RestUserService(IUserService service) {
		this.service = service;
	}

	@Override
	public List<User> getAllUsers() {
		List<UserWithPassword> users = service.getAllUsers();
		List<User> responses = new ArrayList<>(users.size());
		for (UserWithPassword user : users) {
			responses.add(new User(user));
		}
		return responses;
	}

	@Override
	public User getUser(String username) {
		UserWithPassword user = service.getUser(username);
		return convert(user);
	}

	@Override
	public User createUser(UserWithPassword user) {
		UserWithPassword created = service.createUser(user);
		return convert(created);
	}

	@Override
	public User updateUser(UserWithPassword user) {
		UserWithPassword updated = service.updateUser(user);
		return convert(updated);
	}

	@Override
	public User deleteUser(String username) {
		UserWithPassword deleted = service.deleteUser(username);
		return convert(deleted);
	}
	
	private User convert(UserWithPassword uwp) {
		return Objects.isNull(uwp) ? null : new User(uwp);
	}

}
