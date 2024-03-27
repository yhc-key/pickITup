package com.ssafy.pickitup.domain.user.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Rank {

    NORMAL("normal"),
    SUPER("super");

    private final String rank;
}
