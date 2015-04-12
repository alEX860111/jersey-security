package com.example.rest.security;

import java.security.Principal;

import javax.ws.rs.core.SecurityContext;

import com.example.rest.domain.Role;
import com.nimbusds.jwt.ReadOnlyJWTClaimsSet;

final class JWTSecurityContext implements SecurityContext {

	private static final String HTTPS = "https";

	private final Principal principal;

	private final String role;

	private final boolean isSecure;

	public JWTSecurityContext(ReadOnlyJWTClaimsSet claims, String scheme) {
		this.principal = SimplePrincipal.create(claims.getSubject());
		this.role = (String) claims.getCustomClaim(Role.class.getSimpleName().toLowerCase());
		this.isSecure = HTTPS.equals(scheme.toLowerCase());
	}

	@Override
	public Principal getUserPrincipal() {
		return principal;
	}

	@Override
	public boolean isUserInRole(String role) {
		return this.role == null ? false : this.role.equals(role);
	}

	@Override
	public boolean isSecure() {
		return isSecure;
	}

	@Override
	public String getAuthenticationScheme() {
		return null;
	}

}
