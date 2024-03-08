package com.ssafy.pickitup.domain.auth.command;

import com.ssafy.pickitup.domain.auth.command.dto.UserSignupDto;
import com.ssafy.pickitup.domain.auth.entity.Auth;
import com.ssafy.pickitup.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthCommandService {

    private final AuthCommandJpaRepository authCommandJpaRepository;

    public void signup(UserSignupDto userSignupDto) {
        Auth auth = Auth.builder()
            .name(userSignupDto.getUsername())
            .build();
        authCommandJpaRepository.save(auth);
    }
}
