package com.ssafy.pickitup.domain.badge.command;

import com.ssafy.pickitup.domain.badge.command.dto.BadgeCommandResponseDto;
import com.ssafy.pickitup.domain.badge.entity.Badge;
import com.ssafy.pickitup.domain.badge.entity.UserBadge;
import com.ssafy.pickitup.domain.badge.query.BadgeQueryJpaRepository;
import com.ssafy.pickitup.domain.badge.query.BadgeQueryService;
import com.ssafy.pickitup.domain.badge.query.UserBadgeQueryJpaRepository;
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
    private final UserBadgeCommandJpaRepository userBadgeCommandJpaRepository;
    private final UserCommandJpaRepository userCommandJpaRepository;
    private final BadgeQueryJpaRepository badgeQueryJpaRepository;
    private final BadgeQueryService badgeQueryService;

    @Transactional
    @Override
    public BadgeCommandResponseDto renewBadge(Integer userId) {
        log.info("뱃지 갱신하려는 userId : {}", userId);
        User user = userCommandJpaRepository.findById(userId)
            .orElseThrow(UserNotFoundException::new);
        List<String> result = new ArrayList<>();
        List<UserBadge> userBadges = userBadgeQueryJpaRepository.findByUser(user);
        List<UserBadge> notAchievedBadges = badgeQueryService.findNotAchievedBadges(userBadges);

        for (UserBadge userBadge : notAchievedBadges) {
            log.info("갱신 안된 뱃지 개수 : {}", notAchievedBadges.size());
            if (badgeQueryService.isBadgeAchieved(user, userBadge)) {
                userBadge.setAchieved(true);
                result.add(userBadge.getBadge().getName());
                log.info("{} 갱신 성공 ! ", userBadge.getBadge().getName());
            }
        }
        return new BadgeCommandResponseDto(result);
    }

    @Transactional
    @Override
    public void initBadge(Integer userId) {
        log.info("init 시작");
        User user = userCommandJpaRepository.findById(userId).get();
        List<Badge> badges = badgeQueryJpaRepository.findAll();
        for (Badge badge : badges) {
            UserBadge userBadge = UserBadge.builder()
                .badge(badge)
                .user(user)
                .isAchieved(false)
                .build();
            userBadgeCommandJpaRepository.save(userBadge);
        }
        log.info("init 끝");
    }
}
