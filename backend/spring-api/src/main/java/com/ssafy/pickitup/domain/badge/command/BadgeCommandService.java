package com.ssafy.pickitup.domain.badge.command;

import com.ssafy.pickitup.domain.badge.command.dto.BadgeCommandResponseDto;
import com.ssafy.pickitup.domain.user.entity.User;

public interface BadgeCommandService {

    BadgeCommandResponseDto renewBadge(Integer userId);

//    void initBadge(Integer userId);

    void initBadge(User user);
}
