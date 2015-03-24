package com.example.security;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.security.SecureRandom;
import java.text.ParseException;

import org.junit.Before;
import org.junit.Test;

import com.example.domain.Role;
import com.example.domain.Token;
import com.example.domain.User;
import com.example.security.TokenService;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.SignedJWT;

public class TokenServiceTest {

    private byte[] SECRET;

    private TokenService serviceSUT;

    @Before
    public void setUp() {
        JWSSigner signer = createTestSigner();
        serviceSUT = new TokenService(signer);
    }

    private JWSSigner createTestSigner() {
        // Generate random 256-bit (32-byte) shared secret
        SecureRandom random = new SecureRandom();
        SECRET = new byte[32];
        random.nextBytes(SECRET);

        // Create HMAC signer
        return new MACSigner(SECRET);
    }

    @Test
    public void test() throws ParseException, JOSEException {
        User user = new User();
        user.setUsername("username");
        user.setRole(Role.USER);

        Token token = serviceSUT.create(user);

        SignedJWT parsedJWT = SignedJWT.parse(token.getToken());
        JWSVerifier verifier = new MACVerifier(SECRET);
        assertTrue(parsedJWT.verify(verifier));

        // Retrieve the JWT claims
        assertEquals("username", parsedJWT.getJWTClaimsSet().getSubject());
        assertEquals("USER", parsedJWT.getJWTClaimsSet().getCustomClaim("role"));
    }

}
