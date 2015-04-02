package com.example.security;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class TestUserService {

	private User user;

	private UserService service;

	@Before
	public void setUp() {
		user = new User();
		user.setUsername("username");
		user.setPassword("password");
		user.setRole("role");
		service = new UserService();
	}

	@Test
	public void testGetAllUsers() {
		List<User> users = service.getAllUsers();
		assertEquals(1, users.size());
	}

	@Test
	public void testCreate() {
		boolean created = service.createUser(user);
		assertTrue(created);

		List<User> users = service.getAllUsers();
		assertEquals(2, users.size());
		assertTrue(users.contains(user));
	}

	@Test
	public void testCreate_existsAlready() {
		service.createUser(user);

		boolean created = service.createUser(user);
		assertFalse(created);
		assertEquals(2, service.getAllUsers().size());
	}

	@Test
	public void testGet_existing() {
		boolean created = service.createUser(user);
		assertTrue(created);
		assertEquals(user, service.getUser(user.getUsername()));
	}

	@Test
	public void testGet_nonExisting() {
		assertNull(service.getUser(user.getUsername()));
	}

	@Test
	public void testUpdate_existingUser() {
		boolean created = service.createUser(user);
		assertTrue(created);

		User update = new User();
		update.setUsername(user.getUsername());
		update.setPassword(user.getPassword());
		update.setRole("B");

		boolean updated = service.updateUser(update);
		assertTrue(updated);
		assertEquals(2, service.getAllUsers().size());
	}

	@Test
	public void testUpdate_nonExistingUser() {
		boolean updated = service.updateUser(user);
		assertFalse(updated);
		assertEquals(1, service.getAllUsers().size());
	}

	@Test
	public void testDelete_existing() {
		boolean created = service.createUser(user);
		assertTrue(created);

		boolean deleted = service.deleteUser(user.getUsername());
		assertTrue(deleted);
		assertEquals(1, service.getAllUsers().size());
	}

	@Test
	public void testDelete_nonExisting() {
		boolean deleted = service.deleteUser(user.getUsername());
		assertFalse(deleted);
		assertEquals(1, service.getAllUsers().size());
	}

}
