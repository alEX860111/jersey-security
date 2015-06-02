package com.example.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.example.rest.domain.Role;
import com.example.rest.domain.User;

public class TestUserService {

	private User user;

	private UserService service;

	@Before
	public void setUp() {
		user = new User();
		user.setUsername("username");
		user.setPassword("password");
		user.setRole(Role.USER);
		service = new UserService();
	}

	@Test
	public void testGetAllUsers() {
		List<User> users = service.getAllUsers();
		assertEquals(1, users.size());
	}

	@Test
	public void testCreate() {
		User created = service.createUser(user);
		assertEquals(user, created);

		List<User> users = service.getAllUsers();
		assertEquals(2, users.size());
		assertTrue(users.contains(user));
	}

	@Test
	public void testCreate_existsAlready() {
		service.createUser(user);

		User created = service.createUser(user);
		assertNull(created);
		assertEquals(2, service.getAllUsers().size());
	}

	@Test
	public void testGet_existing() {
		User created = service.createUser(user);
		assertEquals(user, created);
		assertEquals(user, service.getUser(user.getUsername()));
	}

	@Test
	public void testGet_nonExisting() {
		assertNull(service.getUser(user.getUsername()));
	}

	@Test
	public void testDelete_existing() {
		User created = service.createUser(user);
		assertEquals(user, created);

		User deleted = service.deleteUser(user.getUsername());
		assertEquals(user, deleted);
		assertEquals(1, service.getAllUsers().size());
	}

	@Test
	public void testDelete_nonExisting() {
		User deleted = service.deleteUser(user.getUsername());
		assertNull(deleted);
		assertEquals(1, service.getAllUsers().size());
	}

}
