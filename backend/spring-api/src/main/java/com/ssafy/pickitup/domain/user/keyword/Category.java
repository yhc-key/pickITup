package com.ssafy.pickitup.domain.user.keyword;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Category {

    BACKEND("백앤드"),
    FRONTEND("프론트앤드"),
    LANGUAGE("언어"),
    MOBILE("모바일"),
    DATA("데이터"),
    DEVOPS("데브옵스"),
    TESTING_TOOL("테스팅툴"),
    GENERAL("일반");


    private final String category;

}
