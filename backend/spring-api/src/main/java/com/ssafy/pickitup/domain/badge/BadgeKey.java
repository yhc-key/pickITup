package com.ssafy.pickitup.domain.badge;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor

public enum BadgeKey {

    ATTEND_COUNT("attendCount"),
    RECRUIT_VIEW_COUNT("recruitViewCount"),
    RECRUIT_SCRAP_COUNT("recruitScrapCount"),
    BLOG_VIEW_COUNT("blogViewCount"),
    BLOG_SCRAP_COUNT("blogScrapCount"),
    SELF_ANSWER_COUNT("selfAnswerCount"),
    GAME_WIN_COUNT("gameWinCount");

    private final String badgeKey;
}
