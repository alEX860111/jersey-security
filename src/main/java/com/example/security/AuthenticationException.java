package com.example.security;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.example.domain.Message;

public final class AuthenticationException extends WebApplicationException {

	private static final String MESSAGE = "Bad username and/or password.";

	private static final long serialVersionUID = 1L;

	public AuthenticationException() {
		super(MESSAGE, Response.status(Status.BAD_REQUEST)
				.entity(Message.create(MESSAGE)).build());
	}

}
