package com.example.security;

import com.example.domain.Token;
import com.example.domain.User;

public interface ITokenService {

    public Token createToken(User user);

}
