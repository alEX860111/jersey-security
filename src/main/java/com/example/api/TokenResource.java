package com.example.api;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.example.domain.Token;
import com.example.service.ITokenService;

@Path("token")
public class TokenResource {

    private ITokenService service;

    @Inject
    public TokenResource(ITokenService service) {
        this.service = service;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Token get() {
        return service.create();
    }
}