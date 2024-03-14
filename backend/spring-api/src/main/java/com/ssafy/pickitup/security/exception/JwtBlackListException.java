package com.ssafy.pickitup.security.exception;

import io.jsonwebtoken.MalformedJwtException;

public class JwtBlackListException extends MalformedJwtException {

    public JwtBlackListException(String message) {
        super(message);
    }
}
