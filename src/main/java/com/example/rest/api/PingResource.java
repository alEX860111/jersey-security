package com.example.rest.api;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("ping")
public final class PingResource {

	@GET
	@Path("home")
	@RolesAllowed(value = { "USER", "ADMIN" })
	public Response pingHome() {
		return Response.ok().build();
	}

	@GET
	@Path("products")
	@RolesAllowed(value = { "USER", "ADMIN" })
	public Response pingProducts() {
		return Response.ok().build();
	}

	@GET
	@Path("users")
	@RolesAllowed(value = { "ADMIN" })
	public Response pingUsers() {
		return Response.ok().build();
	}

}