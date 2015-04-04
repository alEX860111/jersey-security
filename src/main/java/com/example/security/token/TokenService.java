package com.example.security.token;

import java.time.LocalDateTime;

import javax.inject.Inject;

import com.example.domain.AuthenticationToken;
import com.example.domain.UserWithPassword;
import com.example.security.Role;
import com.example.util.DateConverter;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

final class TokenService implements ITokenService {

	private static final int EXPIRE_TIME_IN_HOURS = 24;

	private final JWSSigner signer;

	@Inject
	public TokenService(JWSSigner signer) {
		this.signer = signer;
	}

	public AuthenticationToken createToken(UserWithPassword user) {
		final JWTClaimsSet claimsSet = createJWTClaimsSet(user);

		final SignedJWT jwt = new SignedJWT(new JWSHeader(JWSAlgorithm.HS256), claimsSet);

		try {
			jwt.sign(signer);
		} catch (JOSEException e) {
			e.printStackTrace();
		}

		// To serialize to compact form, produces something like
		// eyJhbGciOiJIUzI1NiJ9.SGVsbG8sIHdvcmxkIQ.onO9Ihudz3WkiauDO2Uhyuz0Y18UASXlSc1eS0NkWyA
		final String s = jwt.serialize();

		return AuthenticationToken.create(s);
	}

	private JWTClaimsSet createJWTClaimsSet(UserWithPassword user) {
		final JWTClaimsSet claims = new JWTClaimsSet();
		claims.setSubject(user.getUsername());
		claims.setCustomClaim(Role.class.getCanonicalName(), user.getRole());
		claims.setIssuer("https://example.com");

		final LocalDateTime now = LocalDateTime.now();
		claims.setIssueTime(DateConverter.getDate(now));
		claims.setExpirationTime(DateConverter.getDate(now.plusHours(EXPIRE_TIME_IN_HOURS)));
		return claims;
	}

}
