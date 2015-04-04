package com.example.service;

import java.util.List;

import com.example.domain.UserWithPassword;
import com.example.domain.User;

public interface IRestUserService {

	public List<User> getAllUsers();

	public User getUser(String username);

	public User createUser(UserWithPassword user);

	public User updateUser(UserWithPassword user);

	public User deleteUser(String username);

}
