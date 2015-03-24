package com.example.api;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.example.service.IProductService;
import com.example.domain.Product;

@Path("products")
public class ProductsResource {

    private final IProductService service;

    @Inject
    public ProductsResource(IProductService service) {
        this.service = service;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Product> get() {
        return service.getProducts();
    }
}