package com.example.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.example.domain.UserWithPassword;
import com.example.domain.User;

@RunWith(MockitoJUnitRunner.class)
public class TestRestUserService {
	
	@Mock
	private IUserService service;
	
	@InjectMocks
	private RestUserService serviceSUT;

	@Test
	public void testGetAllUsers() {
		when(service.getAllUsers()).thenReturn(Arrays.asList(new UserWithPassword()));
		List<User> users = serviceSUT.getAllUsers();
		assertEquals(1, users.size());
		verify(service).getAllUsers();
	}

	@Test
	public void testGetUser() {
		String username = "username";
		when(service.getUser(username)).thenReturn(new UserWithPassword());
		User user = serviceSUT.getUser(username);
		assertNotNull(user);
		verify(service).getUser(username);
	}

	@Test
	public void testGetUser_doesNotExist() {
		String username = "username";
		when(service.getUser(username)).thenReturn(null);
		User user = serviceSUT.getUser(username);
		assertNull(user);
		verify(service).getUser(username);
	}

	@Test
	public void testCreateUser() {
		UserWithPassword user = new UserWithPassword();
		when(service.createUser(any(UserWithPassword.class))).thenReturn(new UserWithPassword());
		User created = serviceSUT.createUser(user);
		assertNotNull(created);
		verify(service).createUser(any(UserWithPassword.class));
	}

	@Test
	public void testUpdateUser() {
		UserWithPassword user = new UserWithPassword();
		when(service.updateUser(any(UserWithPassword.class))).thenReturn(new UserWithPassword());
		User updated = serviceSUT.updateUser(user);
		assertNotNull(updated);
		verify(service).updateUser(any(UserWithPassword.class));
	}

	@Test
	public void testDeleteUser() {
		String username = "username";
		when(service.deleteUser(username)).thenReturn(new UserWithPassword());
		User deleted = serviceSUT.deleteUser(username);
		assertNotNull(deleted);
		verify(service).deleteUser(username);
	}

}
