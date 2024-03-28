package com.ssafy.pickitup.domain.user.exception;

public class DuplicateScrapException extends RuntimeException {

    public DuplicateScrapException() {
        super("이미 스크랩한 채용 공고입니다.");
    }
}