package com.ssafy.pickitup.domain.badge.command;

import com.ssafy.pickitup.domain.badge.command.dto.BadgeCommandResponseDto;
import com.ssafy.pickitup.domain.badge.query.dto.BadgeQueryResponseDto;
import com.ssafy.pickitup.domain.user.entity.User;
import java.util.List;

public interface BadgeCommandService {

    BadgeCommandResponseDto renewBadge(Integer userId);

    void initBadge(User user);

    List<BadgeQueryResponseDto> findMyBadges(Integer userId);
}
