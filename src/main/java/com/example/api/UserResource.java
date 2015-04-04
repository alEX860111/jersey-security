package com.example.api;

import java.net.URI;
import java.util.List;
import java.util.Objects;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import com.example.domain.Message;
import com.example.domain.UserWithPassword;
import com.example.domain.User;
import com.example.service.IRestUserService;

@Path("users")
@RolesAllowed(value = { "ADMIN" })
public final class UserResource {

	private final IRestUserService service;

	@Inject
	public UserResource(IRestUserService service) {
		this.service = service;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<User> getAllUsers() {
		return service.getAllUsers();
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response createUser(@Valid UserWithPassword request, @Context UriInfo uriInfo) {
		User created = service.createUser(request);
		if (Objects.nonNull(created)) {
			URI location = uriInfo.getBaseUriBuilder().path(UserResource.class).path(request.getUsername()).build();
			return Response.created(location).entity(created).build();
		}
		return Response.status(Status.BAD_REQUEST)
				.entity(Message.create("Username '" + request.getUsername() + "' already exists.")).build();
	}
}