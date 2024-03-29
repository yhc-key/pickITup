package com.ssafy.pickitup;

import com.ssafy.pickitup.domain.auth.command.AuthCommandService;
import com.ssafy.pickitup.domain.auth.command.dto.UserSignupDto;
import com.ssafy.pickitup.domain.selfdocument.command.MainQuestionCommandService;
import com.ssafy.pickitup.domain.selfdocument.command.SubQuestionCommandService;
import com.ssafy.pickitup.domain.selfdocument.command.dto.MainQuestionCommandRequestDto;
import com.ssafy.pickitup.domain.selfdocument.command.dto.SubQuestionCommandRequestDto;
import com.ssafy.pickitup.domain.user.command.repository.KeywordCommandJpaRepository;
import com.ssafy.pickitup.domain.user.command.service.UserCommandService;
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
    private final KeywordCommandJpaRepository keywordCommandJpaRepository;

    @Transactional
    public void init() {

        //auth 정보 저장
        UserSignupDto userSignupDto1 = new UserSignupDto("hscho", "1234", "조현수", "존수존수",
            "hyunsoo@naver.com", "서울시 강남구 역삼동");
        UserSignupDto userSignupDto2 = new UserSignupDto("shno", "12345", "노세희", "올리비아핫세",
            "sehee@naver.com", "서울시 강남구 역삼동");
        UserSignupDto userSignupDto3 = new UserSignupDto("yhcho", "12346", "조용환", "화니",
            "younghwan@naver.com", "서울시 강남구 역삼동");
        authCommandService.signup(userSignupDto1);
        authCommandService.signup(userSignupDto2);
        authCommandService.signup(userSignupDto3);

        MainQuestionCommandRequestDto mainQuestionCommandRequestDto1 = new MainQuestionCommandRequestDto(
            "지원동기");
        MainQuestionCommandRequestDto mainQuestionCommandRequestDto2 = new MainQuestionCommandRequestDto(
            "위기 극복 경험");
        SubQuestionCommandRequestDto subQuestionCommandRequestDto1 = new SubQuestionCommandRequestDto(
            "삼성전자를 지원한 이유와 입사 후 회사에서 이루고 싶은 꿈을 기술하십시오.", "이 회사 너무 잘 나가서요", "삼성");
        SubQuestionCommandRequestDto subQuestionCommandRequestDto2 = new SubQuestionCommandRequestDto(
            "SK 하이닉스의 지원동기와 입사 후 계획", "이천 밥이 그렇게 맛있다던데", "SK");
        SubQuestionCommandRequestDto subQuestionCommandRequestDto3 = new SubQuestionCommandRequestDto(
            "자기소개와 함께 지원 동기를 설명해 주세요.", "저는 조은우입니다", "네이버");
        SubQuestionCommandRequestDto subQuestionCommandRequestDto4 = new SubQuestionCommandRequestDto(
            "프로그램 개발, 알고리즘 풀이 등 SW개발 관련 경험 중 가장 어려웠던 경험과 해결방안에 대해 구체적으로 서술하여 주시기 바랍니다. (과제 개요, 어려웠던 점, 해결방법, 결과 포함)",
            "개발 너무 어려워요..", "삼성");

        mainQuestionCommandService.registerMainQuestion(1, mainQuestionCommandRequestDto1);
        mainQuestionCommandService.registerMainQuestion(1, mainQuestionCommandRequestDto2);
        subQuestionCommandService.registerSubQuestion(1, subQuestionCommandRequestDto1);
        subQuestionCommandService.registerSubQuestion(1, subQuestionCommandRequestDto2);
        subQuestionCommandService.registerSubQuestion(1, subQuestionCommandRequestDto3);
        subQuestionCommandService.registerSubQuestion(2, subQuestionCommandRequestDto4);
    }
}
