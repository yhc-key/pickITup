package com.ssafy.pickitup.domain.user.command;

import com.ssafy.pickitup.domain.auth.command.dto.UserSignupDto;
import com.ssafy.pickitup.domain.auth.entity.Auth;
import com.ssafy.pickitup.domain.user.entity.User;
import com.ssafy.pickitup.domain.user.query.dto.UserResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserCommandService {

    private final UserCommandJpaRepository userCommandJpaRepository;

    @Transactional
    public UserResponseDto create(Auth auth, UserSignupDto userSignupDto) {
        User user = User.builder()
            .nickname(userSignupDto.getNickname())
            .auth(auth)
            .build();
        userCommandJpaRepository.save(user);
        return UserResponseDto.toDto(user);
    }

    @Transactional
    public UserResponseDto create(Auth auth) {
        User user = User.builder()
            .nickname(auth.getName())
            .auth(auth)
            .build();
        userCommandJpaRepository.save(user);
        return UserResponseDto.toDto(user);
    }


}
