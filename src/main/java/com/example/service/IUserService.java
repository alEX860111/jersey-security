package com.example.service;

import java.util.List;

import com.example.domain.UserWithPassword;

public interface IUserService {

	public List<UserWithPassword> getAllUsers();

	public UserWithPassword getUser(String username);

	public UserWithPassword createUser(UserWithPassword user);

	public UserWithPassword updateUser(UserWithPassword user);

	public UserWithPassword deleteUser(String username);

}
