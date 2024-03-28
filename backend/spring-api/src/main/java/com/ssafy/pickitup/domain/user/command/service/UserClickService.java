package com.ssafy.pickitup.domain.user.command.service;

import com.ssafy.pickitup.domain.user.command.repository.UserClickCommandJpaRepository;
import com.ssafy.pickitup.domain.user.entity.UserClick;
import com.ssafy.pickitup.domain.user.query.dto.UserClickResponseDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserClickService {

    private final UserClickCommandJpaRepository userClickCommandJpaRepository;

    public UserClickResponseDto findAllUserClick(Integer authId) {
        List<UserClick> userCLickList = userClickCommandJpaRepository.findAllByUserId(authId);
        UserClickResponseDto userClickResponseDto = UserClickResponseDto.toDto(authId,
            userCLickList);
        return userClickResponseDto;
    }
}
