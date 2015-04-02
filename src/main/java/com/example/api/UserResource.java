package com.example.api;

import java.net.URI;
import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
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
import com.example.security.IUserService;
import com.example.security.Role;
import com.example.security.User;

@Path("users")
@RolesAllowed(value = { Role.ADMIN })
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
	public Response create(User user, @Context UriInfo uriInfo) {
		boolean created = service.createUser(user);
		if (created) {
			URI uri = uriInfo.getBaseUriBuilder().path(UserResource.class).path(user.getUsername()).build();
			return Response.created(uri).entity(user).build();
		}
		return Response.status(Status.BAD_REQUEST)
				.entity(Message.create("Username '" + user.getUsername() + "' already exists.")).build();
	}
}