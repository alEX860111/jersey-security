package com.example.security;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.text.ParseException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import com.example.domain.AuthenticationResponse;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.ReadOnlyJWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

public class TokenServiceTest {

    private static final String USERNAME = "joe";
    
    private static final Role ROLE = Role.USER;

    private static final byte[] SECRET = new byte[] { -21, -67, -8, -17, 25,
            94, -73, 4, 120, 5, 84, -71, -15, -80, 52, -115, 112, 109, 42, -79,
            -95, 93, 79, 38, 108, -96, -91, 111, 44, 43, 10, 104 };

    private User user;

    private TokenService serviceSUT;

    @Before
    public void setUp() {
        user = new User();
        user.setUsername(USERNAME);
        user.setRole(ROLE);

        final JWSSigner signer = new JWSSignerProvider(SECRET).get();
        serviceSUT = new TokenService(signer);
    }

    @Test
    public void testVerification() throws ParseException, JOSEException {
        AuthenticationResponse token = serviceSUT.createToken(user);
        SignedJWT jwt = SignedJWT.parse(token.getToken());

        JWSVerifier verifier = new MACVerifier(SECRET);
        assertTrue(jwt.verify(verifier));
    }

    @Test
    public void testClaims() throws ParseException {
        AuthenticationResponse token = serviceSUT.createToken(user);
        SignedJWT jwt = SignedJWT.parse(token.getToken());

        final ReadOnlyJWTClaimsSet claims = jwt.getJWTClaimsSet();
        assertEquals(USERNAME, claims.getSubject());
        assertEquals(ROLE.name(), claims.getCustomClaim("role"));
        assertEquals("https://example.com", claims.getIssuer());

        LocalDateTime issueTime = getDateTime(claims.getIssueTime());
        LocalDateTime expireTime = getDateTime(claims.getExpirationTime());
        assertEquals(expireTime, issueTime.plusHours(24));
    }

    private LocalDateTime getDateTime(Date date) {
        final Instant instant = Instant.ofEpochMilli(date.getTime());
        return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
    }

}
