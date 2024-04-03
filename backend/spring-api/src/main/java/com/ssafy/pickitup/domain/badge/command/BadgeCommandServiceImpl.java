package com.ssafy.pickitup.domain.badge.command;

import com.ssafy.pickitup.domain.badge.command.dto.BadgeCommandResponseDto;
import com.ssafy.pickitup.domain.badge.entity.Badge;
import com.ssafy.pickitup.domain.badge.entity.UserBadge;
import com.ssafy.pickitup.domain.badge.query.BadgeQueryJpaRepository;
import com.ssafy.pickitup.domain.badge.query.BadgeQueryService;
import com.ssafy.pickitup.domain.badge.query.UserBadgeQueryJpaRepository;
import com.ssafy.pickitup.domain.badge.query.dto.BadgeQueryResponseDto;
import com.ssafy.pickitup.domain.user.command.repository.UserCommandJpaRepository;
import com.ssafy.pickitup.domain.user.entity.User;
import com.ssafy.pickitup.domain.user.exception.UserNotFoundException;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class BadgeCommandServiceImpl implements BadgeCommandService {

    private final UserBadgeQueryJpaRepository userBadgeQueryJpaRepository;
    private final UserCommandJpaRepository userCommandJpaRepository;
    private final BadgeQueryJpaRepository badgeQueryJpaRepository;
    private final BadgeQueryService badgeQueryService;

    @Transactional
    @Override
    public BadgeCommandResponseDto renewBadge(Integer userId) {
        User user = userCommandJpaRepository.findById(userId)
            .orElseThrow(UserNotFoundException::new);
        List<String> result = new ArrayList<>();
        List<UserBadge> userBadges = userBadgeQueryJpaRepository.findByUserId(userId);
        List<UserBadge> notAchievedBadges = badgeQueryService.findNotAchievedBadges(userBadges);

        for (UserBadge userBadge : notAchievedBadges) {
            if (badgeQueryService.isBadgeAchieved(user, userBadge)) {
                userBadge.setAchieved(true);
                result.add(userBadge.getBadge().getName());
            }
        }
        return new BadgeCommandResponseDto(result);
    }

    @Transactional
    public void initBadge(User user) {
        List<Badge> badges = badgeQueryJpaRepository.findAll();
        List<UserBadge> userBadgeList = badges.stream()
            .map(badge -> UserBadge.builder().user(user).badge(badge).isAchieved(false).build())
            .toList();
        user.setUserBadges(userBadgeList);
    }


    @Override
    public List<BadgeQueryResponseDto> findMyBadges(Integer userId) {
        renewBadge(userId);
        return userBadgeQueryJpaRepository.findByUserId(userId)
            .stream()
            .map(UserBadge::toResponse)
            .toList();
    }
}
