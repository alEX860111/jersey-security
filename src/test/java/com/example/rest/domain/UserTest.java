package com.example.rest.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.Before;
import org.junit.Test;

public class UserTest {

	private Validator validator;

	@Before
	public void setUp() {
		Locale.setDefault(Locale.ENGLISH);
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	@Test
	public void testValidate() {
		User user = new User();
		user.setUsername("username");
		user.setPassword("password");
		user.setRole(Role.USER);
		Map<String, String> violations = validate(user);
		assertTrue(violations.isEmpty());
	}

	@Test
	public void testValidate_allNull() {
		User user = new User();
		Map<String, String> violations = validate(user);
		assertEquals(3, violations.size());
		assertEquals("may not be null", violations.get("role"));
		assertEquals("may not be null", violations.get("username"));
		assertEquals("may not be null", violations.get("password"));
	}

	@Test
	public void testValidate_invalidUsername() {
		User user = new User();
		user.setUsername("user/name");
		user.setPassword("pass/word");
		user.setRole(Role.USER);
		Map<String, String> violations = validate(user);
		assertEquals(2, violations.size());
		assertEquals("must match \"\\w+\"", violations.get("username"));
		assertEquals("must match \"\\w+\"", violations.get("password"));
	}

	private Map<String, String> validate(User user) {
		Map<String, String> violations = new HashMap<>();
		validator.validate(user).forEach(
				violation -> violations.put(violation.getPropertyPath().toString(), violation.getMessage()));
		return violations;
	}

}
