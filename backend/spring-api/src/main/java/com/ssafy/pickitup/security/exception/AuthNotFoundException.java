package com.ssafy.pickitup.security.exception;

import java.util.NoSuchElementException;

public class AuthNotFoundException extends NoSuchElementException {

    public AuthNotFoundException() {
        super("입력한 유저가 존재하지 않습니다.");
    }

    public AuthNotFoundException(String message) {
        super(message);
    }
}
