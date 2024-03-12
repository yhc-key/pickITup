package com.ssafy.pickitup;

import com.ssafy.pickitup.domain.auth.command.AuthCommandService;
import com.ssafy.pickitup.domain.auth.command.dto.UserSignupDto;
import com.ssafy.pickitup.domain.user.command.UserCommandService;
import com.ssafy.pickitup.domain.user.query.dto.UserResponseDto;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Component
@RequiredArgsConstructor
public class InitDB {

    private final AuthCommandService authCommandService;
    private final UserCommandService userCommandService;


    @PostConstruct
    @Transactional
    public void init() {
        UserSignupDto userSignupDto = new UserSignupDto("hscho", "1234", "조현수", "존수존수",
            "hyunsoo31@naver.com");
        //auth 정보 저장
        authCommandService.signup(userSignupDto);
    }
}
