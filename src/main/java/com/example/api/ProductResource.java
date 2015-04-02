package com.example.api;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.example.domain.Product;
import com.example.security.Role;
import com.example.service.IProductService;

@Path("products")
public final class ProductResource {

	private final IProductService service;

	@Inject
	public ProductResource(IProductService service) {
		this.service = service;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@RolesAllowed(value = { Role.USER, Role.ADMIN })
	public List<Product> get() {
		return service.getProducts();
	}
}