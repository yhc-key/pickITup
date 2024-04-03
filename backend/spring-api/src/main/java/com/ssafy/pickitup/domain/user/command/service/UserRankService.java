package com.ssafy.pickitup.domain.user.command.service;

import com.ssafy.pickitup.domain.user.command.repository.UserLevelJpaRepository;
import com.ssafy.pickitup.domain.user.entity.User;
import com.ssafy.pickitup.domain.user.entity.UserLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserRankService {

    private final UserLevelJpaRepository userLevelJpaRepository;

    @Transactional
    public User updateLevel(User user) {

        int exp = user.expCalculator();

        UserLevel userLevel = userLevelJpaRepository.findFirstByExpGreaterThanOrderByExpAsc(exp);

        if (userLevel.getLevel() > 1) {
            userLevel.setPrevExp(
                userLevelJpaRepository.findExpByLevel(userLevel.getLevel() - 1).getExp());
            userLevel.setNextExp(
                userLevelJpaRepository.findExpByLevel(userLevel.getLevel()).getExp());
        }
        user.setExp(exp);
        user.setLevel(userLevel.getLevel());
        return user;
    }

    public UserLevel getExpInfo(Integer level) {
        int prevExp = 0;
        int nextExp = 100;
        if (level > 1) {
            prevExp = userLevelJpaRepository.findExpByLevel(level - 1).getExp();
            nextExp = userLevelJpaRepository.findExpByLevel(level).getExp();
        }
        return UserLevel.builder().prevExp(prevExp).nextExp(nextExp).build();
    }
}
