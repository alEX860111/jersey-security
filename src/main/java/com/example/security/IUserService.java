package com.example.security;

import java.util.List;

public interface IUserService {

	public List<User> getAllUsers();

	public boolean createUser(User user);

	public User getUser(String username);

	public boolean updateUser(User user);

	public boolean deleteUser(String username);

}
