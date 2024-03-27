package com.ssafy.pickitup.domain.badge.command;

import com.ssafy.pickitup.domain.badge.command.dto.BadgeCommandResponseDto;

public interface BadgeCommandService {

    BadgeCommandResponseDto renewBadge(Integer userId);

    void initBadge(Integer userId);
}
