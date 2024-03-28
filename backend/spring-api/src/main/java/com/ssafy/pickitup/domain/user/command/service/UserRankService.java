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
        log.info("user level before = {}", user.getLevel());
        if (userLevel == null) {
            user.setLevel(20);
        } else {
            user.setLevel(userLevel.getLevel());
        }
        log.info("user level after = {}", user.getLevel());
        return user;
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
