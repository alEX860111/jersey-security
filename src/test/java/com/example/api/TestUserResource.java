package com.example.api;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;

import java.util.List;

import javax.ws.rs.core.Application;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;

import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.example.security.IUserService;
import com.example.security.User;

@RunWith(MockitoJUnitRunner.class)
public class TestUserResource extends JerseyTest {

	@Mock
	private IUserService service;

	@Override
	protected Application configure() {
		return new ResourceConfig().register(UserResource.class).register(new AbstractBinder() {
			@Override
			protected void configure() {
				bind(service).to(IUserService.class);
			}
		});
	}

	@Test
	public void testGetAllUsers() {
		Response response = target().path("users").request().get();
		List<User> users = response.readEntity(new GenericType<List<User>>() {
		});
		assertTrue(users.isEmpty());
		verify(service).getAllUsers();
	}

}
