package com.ssafy.pickitup.domain.interview.exception;

public class InterviewNotFoundException extends RuntimeException {

    public InterviewNotFoundException() {
        super("Interview not found");
    }
}
