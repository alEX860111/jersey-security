package com.example.rest.security;

import java.text.ParseException;

import javax.inject.Inject;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.core.Response.Status;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jwt.SignedJWT;

final class AuthenticationTokenVerifier implements IAuthenticationTokenVerifier {

	private final JWSVerifier verifier;

	@Inject
	public AuthenticationTokenVerifier(JWSVerifier verifier) {
		this.verifier = verifier;
	}

	@Override
	public SignedJWT verify(String token) {
		boolean verified = false;
		SignedJWT jwt;
		try {
			jwt = SignedJWT.parse(token);
			verified = jwt.verify(verifier);
		} catch (ParseException | JOSEException e) {
			throw new BadRequestException(e.getMessage(), e);
		}

		if (!verified) {
			throw new AuthenticationException(Status.FORBIDDEN, "Token could not be verified.");
		}
		return jwt;
	}

}
