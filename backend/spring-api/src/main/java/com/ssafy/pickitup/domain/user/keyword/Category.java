package com.ssafy.pickitup.domain.user.keyword;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Category {

    BACKEND("Back-end"),
    FRONTEND("Front-end"),
    LANGUAGE("language"),
    INFRA("infra");

    private final String category;

}
