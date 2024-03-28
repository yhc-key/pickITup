package com.ssafy.pickitup.domain.badge.entity;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor

public enum BadgeKey {

    ATTEND_COUNT("attendCount"),
    RECRUIT_VIEW_COUNT("recruitViewCount"),
    RECRUIT_SCRAP_COUNT("recruitScrapCount"),
    SELF_ANSWER_COUNT("selfAnswerCount"),
    GAME_WIN_COUNT("gameWinCount");

    private final String badgeKey;
}
