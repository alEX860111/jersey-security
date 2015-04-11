package com.example.rest.api;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("ping")
@RolesAllowed(value = { "USER", "ADMIN" })
public final class PingResource {

	@GET
	public Response get() {
		return Response.ok().build();
	}

}