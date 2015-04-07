package com.example.rest.api;

import java.net.URI;
import java.util.List;
import java.util.Objects;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import com.example.rest.domain.Message;
import com.example.rest.domain.User;
import com.example.service.IUserService;

@Path("users")
@RolesAllowed(value = { "ADMIN" })
public final class UserResource {

	private final IUserService service;

	@Inject
	public UserResource(IUserService service) {
		this.service = service;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<User> getAllUsers() {
		return service.getAllUsers();
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response createUser(@Valid User user, @Context UriInfo uriInfo) {
		User created = service.createUser(user);
		if (Objects.nonNull(created)) {
			URI location = uriInfo.getBaseUriBuilder().path(UserResource.class).path(user.getUsername()).build();
			return Response.created(location).entity(created).build();
		}
		return Response.status(Status.BAD_REQUEST)
				.entity(Message.create("User '" + user.getUsername() + "' already exists.")).build();
	}

	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateUser(@Valid User user) {
		User updated = service.updateUser(user);
		if (Objects.nonNull(updated)) {
			return Response.ok(updated).build();
		}
		return Response.status(Status.NOT_FOUND)
				.entity(Message.create("User '" + user.getUsername() + "' not found.")).build();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{username}")
	public Response getUser(@PathParam("username") String username) {
		User user = service.getUser(username);
		if (Objects.nonNull(user)) {
			return Response.ok(user).build();
		}
		return Response.status(Status.NOT_FOUND)
				.entity(Message.create("User '" + username + "' not found.")).build();
	}

	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{username}")
	public Response deleteUser(@PathParam("username") String username) {
		User user = service.deleteUser(username);
		if (Objects.nonNull(user)) {
			return Response.ok(user).build();
		}
		return Response.status(Status.NOT_FOUND)
				.entity(Message.create("User '" + username + "' not found.")).build();
	}
}