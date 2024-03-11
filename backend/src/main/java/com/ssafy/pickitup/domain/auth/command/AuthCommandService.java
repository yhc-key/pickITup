package com.ssafy.pickitup.domain.auth.command;

import static com.ssafy.pickitup.domain.auth.api.ApiUtils.*;

import com.ssafy.pickitup.domain.auth.api.ApiUtils;
import com.ssafy.pickitup.domain.auth.api.ApiUtils.ApiResult;
import com.ssafy.pickitup.domain.auth.command.dto.LoginRequestDto;
import com.ssafy.pickitup.domain.auth.command.dto.UserSignupDto;
import com.ssafy.pickitup.domain.auth.entity.Auth;
import com.ssafy.pickitup.domain.auth.query.AuthQueryService;
import com.ssafy.pickitup.domain.auth.query.dto.AuthResponseDto;
import com.ssafy.pickitup.domain.user.exception.UserNotFoundException;
import com.ssafy.pickitup.security.JwtTokenDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthCommandService {


    private final AuthCommandJpaRepository authCommandJpaRepository;
    private final AuthQueryService authQueryService;

    public void signup(UserSignupDto userSignupDto) {
        Auth auth = Auth.builder()
            .username(userSignupDto.getUsername())
            .name(userSignupDto.getName())
            .password(userSignupDto.getPassword())
            .build();
        authCommandJpaRepository.save(auth);
    }

    public AuthResponseDto login(LoginRequestDto loginRequestDto) {
        return authQueryService.login(loginRequestDto);
    }

}
