package com.ssafy.pickitup.domain.badge.query;

import com.ssafy.pickitup.domain.badge.entity.Badge;
import com.ssafy.pickitup.domain.badge.entity.BadgeKey;
import com.ssafy.pickitup.domain.badge.entity.UserBadge;
import com.ssafy.pickitup.domain.user.entity.User;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BadgeQueryServiceImpl implements BadgeQueryService {

    private final UserBadgeQueryJpaRepository userBadgeQueryJpaRepository;

    @Override
    public List<UserBadge> findNotAchievedBadges(List<UserBadge> userBadges) {
        List<UserBadge> notAchievedBadges = new ArrayList<>();
        for (UserBadge userBadge : userBadges) {
            if (!userBadge.isAchieved()) {
                notAchievedBadges.add(userBadge);
            }
        }
        return notAchievedBadges;
    }

    @Override
    public boolean isBadgeAchieved(User user, UserBadge userBadge) {
        Badge badge = userBadge.getBadge();
        BadgeKey badgeKey = badge.getBadgeKey();
        return switch (badgeKey) {
            case ATTEND_COUNT -> user.getAttendCount() > badge.getValue();
            case GAME_WIN_COUNT -> user.getGameWinCount() > badge.getValue();
            case SELF_ANSWER_COUNT -> user.getSelfAnswerCount() > badge.getValue();
            case RECRUIT_VIEW_COUNT -> user.getRecruitViewCount() > badge.getValue();
            case RECRUIT_SCRAP_COUNT -> user.getRecruitScrapCount() > badge.getValue();
        };
    }

    @Override
    public int myBadgeCount(Integer userId) {
//        return 0;
        return userBadgeQueryJpaRepository.countAchievedBadgesByUserId(userId);
    }
}
