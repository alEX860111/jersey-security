package com.example.rest.api;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.example.rest.domain.Product;
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
	@RolesAllowed(value = { "USER", "ADMIN" })
	public List<Product> get() {
		return service.getProducts();
	}
}