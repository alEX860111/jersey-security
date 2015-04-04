package com.example.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.example.domain.UserWithPassword;
import com.example.security.Role;

public class TestUserService {

	private UserWithPassword user;

	private UserService service;

	@Before
	public void setUp() {
		user = new UserWithPassword();
		user.setUsername("username");
		user.setPassword("password");
		user.setRole(Role.USER);
		service = new UserService();
	}

	@Test
	public void testGetAllUsers() {
		List<UserWithPassword> users = service.getAllUsers();
		assertEquals(1, users.size());
	}

	@Test
	public void testCreate() {
		UserWithPassword created = service.createUser(user);
		assertEquals(user, created);

		List<UserWithPassword> users = service.getAllUsers();
		assertEquals(2, users.size());
		assertTrue(users.contains(user));
	}

	@Test
	public void testCreate_existsAlready() {
		service.createUser(user);

		UserWithPassword created = service.createUser(user);
		assertNull(created);
		assertEquals(2, service.getAllUsers().size());
	}

	@Test
	public void testGet_existing() {
		UserWithPassword created = service.createUser(user);
		assertEquals(user, created);
		assertEquals(user, service.getUser(user.getUsername()));
	}

	@Test
	public void testGet_nonExisting() {
		assertNull(service.getUser(user.getUsername()));
	}

	@Test
	public void testUpdate_existingUser() {
		UserWithPassword created = service.createUser(user);
		assertEquals(user, created);

		UserWithPassword update = new UserWithPassword();
		update.setUsername(user.getUsername());
		update.setPassword(user.getPassword());
		update.setRole(Role.ADMIN);

		UserWithPassword updated = service.updateUser(update);
		assertEquals(update, updated);
		assertEquals(2, service.getAllUsers().size());
	}

	@Test
	public void testUpdate_nonExistingUser() {
		UserWithPassword updated = service.updateUser(user);
		assertNull(updated);
		assertEquals(1, service.getAllUsers().size());
	}

	@Test
	public void testDelete_existing() {
		UserWithPassword created = service.createUser(user);
		assertEquals(user, created);

		UserWithPassword deleted = service.deleteUser(user.getUsername());
		assertEquals(user, deleted);
		assertEquals(1, service.getAllUsers().size());
	}

	@Test
	public void testDelete_nonExisting() {
		UserWithPassword deleted = service.deleteUser(user.getUsername());
		assertNull(deleted);
		assertEquals(1, service.getAllUsers().size());
	}

}
