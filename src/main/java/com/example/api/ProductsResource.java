package com.example.api;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;

import com.example.domain.Product;
import com.example.security.Role;
import com.example.service.IProductService;

@Path("products")
public class ProductsResource {

    private final IProductService service;

    @Inject
    public ProductsResource(IProductService service) {
        this.service = service;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed(value = { Role.USER })
    public List<Product> get(@Context SecurityContext sc) {
    	System.out.println(sc.isUserInRole("USER"));
        return service.getProducts();
    }
}