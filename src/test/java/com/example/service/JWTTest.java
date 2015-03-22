package com.example.service;

import static com.example.service.TokenService.SECRET;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.security.NoSuchAlgorithmException;
import java.text.ParseException;

import org.junit.Test;

import com.example.domain.Token;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.SignedJWT;

public class JWTTest {

    @Test
    public void test() throws NoSuchAlgorithmException, JOSEException, ParseException {
        TokenService service = new TokenService();
        Token token = service.create();

        SignedJWT parsedJWT = SignedJWT.parse(token.getToken());

        JWSVerifier verifier = new MACVerifier(SECRET);

        assertTrue(parsedJWT.verify(verifier));

        // Retrieve the JWT claims
        assertEquals("alice", parsedJWT.getJWTClaimsSet().getSubject());
        assertEquals("user", parsedJWT.getJWTClaimsSet().getCustomClaim("role"));
    }

}
