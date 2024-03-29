package com.ssafy.pickitup.domain.user.command.service;

import com.ssafy.pickitup.domain.user.command.repository.UserCommandJpaRepository;
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
    private final UserCommandJpaRepository userCommandJpaRepository;

    @Transactional
    public User updateLevel(User user) {

        int exp = user.expCalculator();

        UserLevel userLevel = userLevelJpaRepository.findFirstByExpGreaterThanOrderByExpAsc(
            exp);

        if (userLevel.getLevel() > 1) {
            userLevel.setPrevExp(
                userLevelJpaRepository.findExpByLevel(userLevel.getLevel() - 1).getExp());
            userLevel.setNextExp(
                userLevelJpaRepository.findExpByLevel(userLevel.getLevel()).getExp());
            log.info("userLevel.getPrevExp = {},", userLevel.getPrevExp());
            log.info("userLevel.getNextExp = {},", userLevel.getNextExp());
        }
        log.info("user level before = {}", user.getLevel());
        log.info("user exp before = {}", user.getExp());
        user.setExp(exp);

        if (userLevel == null) {
            user.setLevel(30);
        } else {
            user.setLevel(userLevel.getLevel());
        }
        log.debug("user level after = {}", user.getLevel());
        log.debug("user exp after = {}", user.getExp());
        return user;
    }

    public UserLevel getExpInfo(Integer level) {
        int prevExp = 0;
        int nextExp = 100;
        if (level > 1) {
            prevExp = userLevelJpaRepository.findExpByLevel(level - 1).getExp();
            nextExp = userLevelJpaRepository.findExpByLevel(level).getExp();
            log.debug("userLevel.getPrevExp = {},", prevExp);
            log.debug("userLevel.getNextExp = {},", nextExp);
        }
        return UserLevel.builder().prevExp(prevExp).nextExp(nextExp).build();
    }
//    @Transactional
//    public void updateLevel(Integer userId, Integer exp) {
//        User user = userCommandJpaRepository.findById(userId)
//            .orElseThrow(() -> new UserNotFoundException("user not found"));
//        UserLevel userLevel = userLevelJpaRepository.findFirstByExpGreaterThanOrderByExpAsc(
//            exp);
//        log.info("user level before = {}", user.getLevel());
//        user.setLevel(userLevel.getLevel());
//        log.info("user level after = {}", user.getLevel());
//    }

}
