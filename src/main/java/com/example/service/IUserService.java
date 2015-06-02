package com.example.service;

import java.util.List;

import com.example.rest.domain.User;

public interface IUserService {

	public List<User> getAllUsers();

	public User getUser(String username);

	public User createUser(User user);

	public User deleteUser(String username);

}
