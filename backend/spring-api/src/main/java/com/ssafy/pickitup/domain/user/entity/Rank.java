package com.ssafy.pickitup.domain.user.entity;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public enum Rank {

    NORMAL("normal"),
    SUPER("super");

    private final String rank;
}
