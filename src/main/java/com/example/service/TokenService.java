package com.example.service;

import java.security.SecureRandom;
import java.util.Date;

import com.example.domain.Token;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

final class TokenService implements ITokenService {

    public static byte[] SECRET;

    static {
        SecureRandom random = new SecureRandom();
        SECRET = new byte[32];
        random.nextBytes(SECRET);
    }

    public Token create() {
        // Generate random 256-bit (32-byte) shared secret

        // Create HMAC signer
        JWSSigner signer = new MACSigner(SECRET);

        // Prepare JWT with claims set
        JWTClaimsSet claimsSet = new JWTClaimsSet();
        claimsSet.setSubject("alice");
        claimsSet.setCustomClaim("role", "user");
        claimsSet.setIssueTime(new Date());
        claimsSet.setIssuer("https://c2id.com");

        SignedJWT signedJWT = new SignedJWT(new JWSHeader(JWSAlgorithm.HS256), claimsSet);

        // Apply the HMAC
        try {
            signedJWT.sign(signer);
        } catch (JOSEException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        // To serialize to compact form, produces something like
        // eyJhbGciOiJIUzI1NiJ9.SGVsbG8sIHdvcmxkIQ.onO9Ihudz3WkiauDO2Uhyuz0Y18UASXlSc1eS0NkWyA
        String s = signedJWT.serialize();
        

        Token token = new Token();
        token.setToken(s);
        return token;
    }

}
