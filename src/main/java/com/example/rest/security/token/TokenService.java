package com.example.rest.security.token;

import java.time.LocalDateTime;

import javax.inject.Inject;

import com.example.rest.domain.AuthenticationToken;
import com.example.rest.domain.Role;
import com.example.rest.domain.User;
import com.example.util.DateConverter;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

final class TokenService implements ITokenService {

	private static final String ISSUER = "http://localhost:8080";

	private static final int EXPIRE_TIME_IN_HOURS = 24;

	private final JWSSigner signer;

	@Inject
	public TokenService(JWSSigner signer) {
		this.signer = signer;
	}

	public AuthenticationToken createToken(User user) {
		final SignedJWT jwt = createJWT(user);
		try {
			jwt.sign(signer);
		} catch (JOSEException e) {
			throw new IllegalStateException("JWT could not be signed.", e);
		}
		return AuthenticationToken.create(jwt.serialize());
	}

	private SignedJWT createJWT(User user) {
		final JWTClaimsSet claims = createJWTClaimsSet(user);
		return new SignedJWT(new JWSHeader(JWSAlgorithm.HS256), claims);
	}

	private JWTClaimsSet createJWTClaimsSet(User user) {
		final JWTClaimsSet claims = new JWTClaimsSet();
		claims.setSubject(user.getUsername());
		claims.setCustomClaim(Role.class.getCanonicalName(), user.getRole());
		claims.setIssuer(ISSUER);
		final LocalDateTime now = LocalDateTime.now();
		claims.setIssueTime(DateConverter.getDate(now));
		claims.setExpirationTime(DateConverter.getDate(now.plusHours(EXPIRE_TIME_IN_HOURS)));
		return claims;
	}

}
