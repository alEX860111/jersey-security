package com.example.rest.security;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.example.rest.domain.Message;

final class AuthenticationException extends WebApplicationException {

	private static final long serialVersionUID = 1L;

	public AuthenticationException(Status status, String msg) {
		super(msg, Response.status(status).entity(Message.create(msg)).build());
	}

}
