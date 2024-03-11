package com.ssafy.pickitup.domain.user.command;

import com.ssafy.pickitup.domain.auth.command.dto.UserSignupDto;
import com.ssafy.pickitup.domain.auth.entity.Auth;
import com.ssafy.pickitup.domain.user.entity.User;
import com.ssafy.pickitup.domain.user.query.dto.UserResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserCommandService {

    private final UserCommandJpaRepository userCommandJpaRepository;

    public UserResponseDto create(UserSignupDto userSignupDto) {
        User user = User.builder()
                .nickname(userSignupDto.getNickname())
                .email(userSignupDto.getEmail())
                .build();
        userCommandJpaRepository.save(user);
        return UserResponseDto.toDto(user);
    }


}
