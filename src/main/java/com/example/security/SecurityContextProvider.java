package com.example.security;

import java.text.ParseException;
import java.time.LocalDateTime;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;

import com.example.util.DateConverter;
import com.nimbusds.jwt.ReadOnlyJWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

final class SecurityContextProvider implements ISecurityContextProvider {

	@Override
	public SecurityContext get(SignedJWT jwt, UriInfo uriInfo) {
		ReadOnlyJWTClaimsSet claims;
		try {
			claims = jwt.getJWTClaimsSet();
		} catch (ParseException e) {
			throw new BadRequestException(e.getMessage(), e);
		}

		if (isExpired(claims)) {
			throw new AuthenticationException(Status.FORBIDDEN, "Token expired.");
		}
		return new JWTSecurityContext(claims, uriInfo.getRequestUri().getScheme());
	}

	private boolean isExpired(ReadOnlyJWTClaimsSet claims) {
		return DateConverter.getDateTime(claims.getExpirationTime()).isBefore(LocalDateTime.now());
	}

}
