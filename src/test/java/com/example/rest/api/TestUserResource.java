package com.example.rest.api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.example.rest.api.UserResource;
import com.example.rest.domain.Message;
import com.example.rest.domain.Role;
import com.example.rest.domain.User;
import com.example.service.IUserService;

@RunWith(MockitoJUnitRunner.class)
public class TestUserResource extends JerseyTest {

	private static final class Request {
		public String getUsername() {return "joe";}
		public String getPassword() {return "pw";}
		public Role getRole() {return Role.USER;}
	}

	@Mock
	private IUserService service;

	private Request request;

	private User user;

	@Override
	protected Application configure() {
		return new ResourceConfig().register(UserResource.class).register(new AbstractBinder() {
			@Override
			protected void configure() {
				bind(service).to(IUserService.class);
			}
		});
	}

	@Before
	public void setUp() throws Exception {
		super.setUp();
		request = new Request();
		user = new User();
		user.setUsername(request.getUsername());
		user.setPassword(request.getPassword());
		user.setRole(request.getRole());
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
		when(service.createUser(any(User.class))).thenReturn(user);

		Response response = target().path("users").request().post(Entity.json(request));

		assertEquals(Status.CREATED.getStatusCode(), response.getStatus());
		assertEquals("/users/joe", response.getHeaders().getFirst("Location"));
		User createdResponse = response.readEntity(User.class);
		assertEquals(user.getUsername(), createdResponse.getUsername());
		assertEquals(user.getRole(), createdResponse.getRole());
		assertNull(createdResponse.getPassword());
		verify(service).createUser(any(User.class));
	}

	@Test
	public void testCreateUser_userExistsAlready() {
		when(service.createUser(any(User.class))).thenReturn(null);

		Response response = target().path("users").request().post(Entity.json(request));

		assertEquals(Status.BAD_REQUEST.getStatusCode(), response.getStatus());
		assertNull(response.getHeaders().getFirst("Location"));
		Message msg = response.readEntity(Message.class);
		assertEquals("User 'joe' already exists.", msg.getMessage());
		verify(service).createUser(any(User.class));
	}

	@Test
	public void testUpdateUser() {
		when(service.updateUser(any(User.class))).thenReturn(user);

		Response response = target().path("users").request().put(Entity.json(request));

		assertEquals(Status.OK.getStatusCode(), response.getStatus());
		User updatedResponse = response.readEntity(User.class);
		assertEquals(user.getUsername(), updatedResponse.getUsername());
		assertEquals(user.getRole(), updatedResponse.getRole());
		assertNull(updatedResponse.getPassword());
		verify(service).updateUser(any(User.class));
	}

	@Test
	public void testUpdateUser_notFound() {
		when(service.updateUser(any(User.class))).thenReturn(null);

		Response response = target().path("users").request().put(Entity.json(request));

		assertEquals(Status.NOT_FOUND.getStatusCode(), response.getStatus());
		Message msg = response.readEntity(Message.class);
		assertNotNull(msg);
		assertEquals("User 'joe' not found.", msg.getMessage());
		verify(service).updateUser(any(User.class));
	}

	@Test
	public void testGetUser() {
		String username = request.getUsername();
		when(service.getUser(username)).thenReturn(user);
		Response response = target().path("users").path(username).request().get();
		assertEquals(Status.OK.getStatusCode(), response.getStatus());
		User getResponse = response.readEntity(User.class);
		assertEquals(user.getUsername(), getResponse.getUsername());
		assertEquals(user.getRole(), getResponse.getRole());
		assertNull(getResponse.getPassword());
		verify(service).getUser(username);
	}

	@Test
	public void testGetUser_notFound() {
		String username = request.getUsername();
		when(service.getUser(username)).thenReturn(null);
		Response response = target().path("users").path(username).request().get();
		assertEquals(Status.NOT_FOUND.getStatusCode(), response.getStatus());
		Message msg = response.readEntity(Message.class);
		assertNotNull(msg);
		assertEquals("User 'joe' not found.", msg.getMessage());
		verify(service).getUser(username);
	}

	@Test
	public void testDeleteUser() {
		String username = request.getUsername();
		when(service.deleteUser(username)).thenReturn(user);
		Response response = target().path("users").path(username).request().delete();
		assertEquals(Status.OK.getStatusCode(), response.getStatus());
		User deletedResponse = response.readEntity(User.class);
		assertEquals(user.getUsername(), deletedResponse.getUsername());
		assertEquals(user.getRole(), deletedResponse.getRole());
		assertNull(deletedResponse.getPassword());
		verify(service).deleteUser(username);
	}

	@Test
	public void testDeleteUser_notFound() {
		String username = request.getUsername();
		when(service.deleteUser(username)).thenReturn(null);
		Response response = target().path("users").path(username).request().delete();
		assertEquals(Status.NOT_FOUND.getStatusCode(), response.getStatus());
		Message msg = response.readEntity(Message.class);
		assertNotNull(msg);
		assertEquals("User 'joe' not found.", msg.getMessage());
		verify(service).deleteUser(username);
	}

}
