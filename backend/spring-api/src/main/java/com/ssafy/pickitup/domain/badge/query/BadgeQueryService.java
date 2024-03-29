package com.ssafy.pickitup.domain.badge.query;

import com.ssafy.pickitup.domain.badge.entity.UserBadge;
import com.ssafy.pickitup.domain.badge.query.dto.BadgeQueryResponseDto;
import com.ssafy.pickitup.domain.user.entity.User;
import java.util.List;

public interface BadgeQueryService {

    List<UserBadge> findNotAchievedBadges(List<UserBadge> userBadges);

    boolean isBadgeAchieved(User user, UserBadge userBadge);

    int myBadgeCount(Integer userId);

    List<BadgeQueryResponseDto> findMyBadges(Integer userId);
}
