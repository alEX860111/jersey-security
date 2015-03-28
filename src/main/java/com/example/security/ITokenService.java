package com.example.security;

import com.example.domain.AuthenticationResponse;

public interface ITokenService {

    public AuthenticationResponse createToken(User user);

}
