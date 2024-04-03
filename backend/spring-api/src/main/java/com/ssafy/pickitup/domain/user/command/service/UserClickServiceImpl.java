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
public class UserClickServiceImpl implements UserClickService{

    private final UserClickCommandJpaRepository userClickCommandJpaRepository;

    @Override
    public UserClickResponseDto findAllUserClick(Integer authId) {
        List<UserClick> userCLickList = userClickCommandJpaRepository.findAllByUserId(authId);
        return UserClickResponseDto.toDto(authId,
            userCLickList);
    }
}
