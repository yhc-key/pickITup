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
        UserSignupDto userSignupDto1 = new UserSignupDto("hscho", "1234", "조현수", "존수존수",
            "hyunsoo@naver.com");
        UserSignupDto userSignupDto2 = new UserSignupDto("shno", "12345", "노세희", "올리비아핫세",
            "sehee@naver.com");
        UserSignupDto userSignupDto3 = new UserSignupDto("yhcho", "12346", "조용환", "화니",
            "younghwan@naver.com");
        //auth 정보 저장
        authCommandService.signup(userSignupDto1);
        authCommandService.signup(userSignupDto2);
        authCommandService.signup(userSignupDto3);

    }
}
