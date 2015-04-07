package com.example.rest.security;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.example.rest.domain.Role;
import com.example.rest.security.JWTSecurityContext;
import com.nimbusds.jwt.JWTClaimsSet;

public class TestJWTSecurityContext {

	private JWTClaimsSet claims;

	@Before
	public void setUp() {
		claims = new JWTClaimsSet();
		claims.setSubject("subject");
		claims.setCustomClaim(Role.class.getCanonicalName(), Role.USER.name());
	}

	@Test
	public void testCreate() {
		JWTSecurityContext ctx = new JWTSecurityContext(claims, "http");
		assertEquals("subject", ctx.getUserPrincipal().getName());
		assertTrue(ctx.isUserInRole(Role.USER.name()));
		assertFalse(ctx.isUserInRole(Role.ADMIN.name()));
		assertNull(ctx.getAuthenticationScheme());
	}

	@Test
	public void testCreate_insecure() {
		JWTSecurityContext ctx = new JWTSecurityContext(claims, "http");
		assertFalse(ctx.isSecure());
	}

	@Test
	public void testCreate_secure() {
		JWTSecurityContext ctx = new JWTSecurityContext(claims, "https");
		assertTrue(ctx.isSecure());
	}

	@Test
	public void testCreate_emptyClaims() {
		JWTSecurityContext ctx = new JWTSecurityContext(new JWTClaimsSet(), "http");
		assertNull(ctx.getUserPrincipal().getName());
		assertFalse(ctx.isUserInRole("USER"));
		assertFalse(ctx.isUserInRole("ADMIN"));
		assertNull(ctx.getAuthenticationScheme());
	}

}
