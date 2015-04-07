package com.example.rest.security;

import com.nimbusds.jwt.SignedJWT;

interface IAuthenticationTokenVerifier {

	public SignedJWT verify(String token);

}
