package com.example.api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.example.domain.Message;
import com.example.domain.UserWithPassword;
import com.example.domain.User;
import com.example.security.Role;
import com.example.service.IRestUserService;

@RunWith(MockitoJUnitRunner.class)
public class TestUserResource extends JerseyTest {

	@Mock
	private IRestUserService service;

	@Override
	protected Application configure() {
		return new ResourceConfig().register(UserResource.class).register(new AbstractBinder() {
			@Override
			protected void configure() {
				bind(service).to(IRestUserService.class);
			}
		});
	}

	@Test
	public void testGetAllUsers() {
		Response response = target().path("users").request().get();
		assertEquals(Status.OK.getStatusCode(), response.getStatus());
		List<User> users = response.readEntity(new GenericType<List<User>>() {});
		assertTrue(users.isEmpty());
		verify(service).getAllUsers();
	}

	@Test
	public void testCreateUser() {
		UserWithPassword request = new UserWithPassword();
		request.setUsername("joe");
		request.setPassword("pw");
		request.setRole(Role.USER);
		User created = new User();
		created.setRole(request.getRole());
		created.setUsername(request.getUsername());
		when(service.createUser(any(UserWithPassword.class))).thenReturn(created);

		Response response = target().path("users").request().post(Entity.entity(request, MediaType.APPLICATION_JSON));

		assertEquals(Status.CREATED.getStatusCode(), response.getStatus());
		assertEquals("/users/joe", response.getHeaders().getFirst("Location"));
		User createdResponse = response.readEntity(User.class);
		assertEquals(created.getUsername(), createdResponse.getUsername());
		assertEquals(created.getRole(), createdResponse.getRole());
		verify(service).createUser(any(UserWithPassword.class));
	}

	@Test
	public void testCreateUser_userExistsAlready() {
		UserWithPassword user = new UserWithPassword();
		user.setUsername("joe");
		user.setPassword("pw");
		user.setRole(Role.USER);
		when(service.createUser(any(UserWithPassword.class))).thenReturn(null);

		Response response = target().path("users").request().post(Entity.entity(user, MediaType.APPLICATION_JSON));

		assertEquals(Status.BAD_REQUEST.getStatusCode(), response.getStatus());
		assertNull(response.getHeaders().getFirst("Location"));
		Message msg = response.readEntity(Message.class);
		assertEquals("Username 'joe' already exists.", msg.getMessage());
		verify(service).createUser(any(UserWithPassword.class));
	}

}
