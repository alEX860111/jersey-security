package com.example.security;

import java.util.Date;

import javax.inject.Inject;

import com.example.domain.Token;
import com.example.domain.User;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

final class TokenService implements ITokenService {

    private final JWSSigner signer;

    @Inject
    public TokenService(JWSSigner signer) {
        this.signer = signer;
    }

    public Token create(User user) {
        // Prepare JWT with claims set
        JWTClaimsSet claimsSet = new JWTClaimsSet();
        claimsSet.setSubject(user.getUsername());
        claimsSet.setCustomClaim("role", user.getRole());
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
