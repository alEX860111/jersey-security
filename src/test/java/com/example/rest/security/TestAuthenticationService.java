package com.example.rest.security;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.example.rest.domain.AuthenticationRequest;
import com.example.rest.domain.AuthenticationToken;
import com.example.rest.domain.User;
import com.example.rest.security.AuthenticationException;
import com.example.rest.security.AuthenticationService;
import com.example.rest.security.token.ITokenService;
import com.example.service.IUserService;

@RunWith(MockitoJUnitRunner.class)
public class TestAuthenticationService {

	private static final String USERNAME = "username";

	private static final String PASSWORD = "password";

	@Mock
	private ITokenService tokenService;

	@Mock
	private IUserService userService;

	private AuthenticationRequest request;

	private User user;

	private AuthenticationToken token;

	@InjectMocks
	private AuthenticationService serviceSUT;

	@Before
	public void setUp() {
		request = new AuthenticationRequest();
		user = new User();
		user.setUsername(USERNAME);
		user.setPassword(PASSWORD);
		token = new AuthenticationToken();
	}

	@Test
	public void testAuthenticate() {
		request.setUsername(USERNAME);
		request.setPassword(PASSWORD);
		when(userService.getUser(USERNAME)).thenReturn(user);
		when(tokenService.createToken(user)).thenReturn(token);
		AuthenticationToken authToken = serviceSUT.authenticate(request);
		assertEquals(token, authToken);
		verify(userService).getUser(USERNAME);
		verify(tokenService).createToken(user);
	}

	@Test(expected = AuthenticationException.class)
	public void testAuthenticate_userDoesNotExist() {
		request.setUsername("unknown");
		when(userService.getUser("unknown")).thenReturn(null);
		serviceSUT.authenticate(request);
	}

	@Test(expected = AuthenticationException.class)
	public void testAuthenticate_wrongPassword() {
		request.setUsername(USERNAME);
		request.setPassword("wrongPassword");
		when(userService.getUser(USERNAME)).thenReturn(user);
		serviceSUT.authenticate(request);
	}

}
