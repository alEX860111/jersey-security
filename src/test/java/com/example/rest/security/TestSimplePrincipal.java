package com.example.rest.security;

import static org.junit.Assert.*;

import java.security.Principal;

import org.junit.Test;

import com.example.rest.security.SimplePrincipal;

public class TestSimplePrincipal {

	private static final String NAME = "name";

	@Test
	public void testCreate() {
		Principal principal = SimplePrincipal.create(NAME);
		assertEquals(NAME, principal.getName());
	}

}
