package com.ssafy.pickitup.domain.user.command.service;

import com.ssafy.pickitup.domain.user.command.repository.UserCommandJpaRepository;
import com.ssafy.pickitup.domain.user.entity.User;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserMongoCommandService {

    private final UserCommandJpaRepository userCommandJpaRepository;
    private final UserCommandService userCommandService;

    @Transactional
    public void migration() {
        List<User> userList = userCommandJpaRepository.findAll();
        for (User user : userList) {
            userCommandService.updateUserMongo(user);
        }
    }
}
