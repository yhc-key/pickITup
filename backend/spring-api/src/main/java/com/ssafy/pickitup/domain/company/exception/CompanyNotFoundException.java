package com.ssafy.pickitup.domain.company.exception;

public class CompanyNotFoundException extends RuntimeException {

    public CompanyNotFoundException() {
        super("해당하는 회사를 찾을 수 없습니다.");
    }
}
