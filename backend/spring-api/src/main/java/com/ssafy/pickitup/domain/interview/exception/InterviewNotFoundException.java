package com.ssafy.pickitup.domain.interview.exception;

public class InterviewNotFoundException extends RuntimeException {

    public InterviewNotFoundException(String message) {
      super(message);
    }
}
