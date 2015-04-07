package com.example.rest.security;

import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;

import com.nimbusds.jwt.SignedJWT;

interface ISecurityContextProvider {

	public SecurityContext get(SignedJWT jwt, UriInfo uriInfo);

}