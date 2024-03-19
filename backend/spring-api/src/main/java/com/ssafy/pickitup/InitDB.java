package com.ssafy.pickitup;

import com.ssafy.pickitup.domain.auth.command.AuthCommandService;
import com.ssafy.pickitup.domain.auth.command.dto.UserSignupDto;
import com.ssafy.pickitup.domain.selfdocument.command.MainQuestionCommandService;
import com.ssafy.pickitup.domain.selfdocument.command.SubQuestionCommandService;
import com.ssafy.pickitup.domain.selfdocument.command.dto.MainQuestionCommandRequestDto;
import com.ssafy.pickitup.domain.selfdocument.command.dto.SubQuestionCommandRequestDto;
import com.ssafy.pickitup.domain.user.command.UserCommandService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Component
@RequiredArgsConstructor
public class InitDB {

    private final AuthCommandService authCommandService;
    private final UserCommandService userCommandService;
    private final MainQuestionCommandService mainQuestionCommandService;
    private final SubQuestionCommandService subQuestionCommandService;

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

        MainQuestionCommandRequestDto mainQuestionCommandRequestDto1 = new MainQuestionCommandRequestDto(
            "메인 질문 기본값1");
        MainQuestionCommandRequestDto mainQuestionCommandRequestDto2 = new MainQuestionCommandRequestDto(
            "메인 질문 기본값2");
        SubQuestionCommandRequestDto subQuestionCommandRequestDto1 = new SubQuestionCommandRequestDto(
            "서브1", "내용1", "삼성");
        SubQuestionCommandRequestDto subQuestionCommandRequestDto2 = new SubQuestionCommandRequestDto(
            "서브2", "내용2", "SK");
        SubQuestionCommandRequestDto subQuestionCommandRequestDto3 = new SubQuestionCommandRequestDto(
            "서브3", "내용3", "네이버");
        SubQuestionCommandRequestDto subQuestionCommandRequestDto4 = new SubQuestionCommandRequestDto(
            "서브4", "내용4", "카카오");

        authCommandService.signup(userSignupDto1);
        authCommandService.signup(userSignupDto2);
        authCommandService.signup(userSignupDto3);

        mainQuestionCommandService.registerMainQuestion(1, mainQuestionCommandRequestDto1);
        mainQuestionCommandService.registerMainQuestion(1, mainQuestionCommandRequestDto2);
        subQuestionCommandService.registerSubQuestion(1, subQuestionCommandRequestDto1);
        subQuestionCommandService.registerSubQuestion(1, subQuestionCommandRequestDto2);
        subQuestionCommandService.registerSubQuestion(1, subQuestionCommandRequestDto3);
        subQuestionCommandService.registerSubQuestion(2, subQuestionCommandRequestDto4);
    }
}
