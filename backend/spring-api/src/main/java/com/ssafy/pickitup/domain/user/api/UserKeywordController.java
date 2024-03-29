package com.ssafy.pickitup.domain.user.api;

import static com.ssafy.pickitup.global.api.ApiUtils.success;

import com.ssafy.pickitup.domain.user.command.service.UserCommandService;
import com.ssafy.pickitup.domain.user.query.UserQueryService;
import com.ssafy.pickitup.domain.user.query.dto.KeywordRequestDto;
import com.ssafy.pickitup.domain.user.query.dto.KeywordResponseDto;
import com.ssafy.pickitup.global.annotation.AuthID;
import com.ssafy.pickitup.global.api.ApiUtils.ApiResult;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = {"https://pickitup.online", "http://localhost:3000",
    "http://localhost:8080", "https://spring.pickitup.online"}, exposedHeaders = "*")
@RequestMapping("/users/keywords")
public class UserKeywordController {

    private final UserCommandService userCommandService;
    private final UserQueryService userQueryService;

    @Operation(summary = "회원 키워드 추가 API")
    @PostMapping
    public ApiResult<?> addUserKeyword(@AuthID Integer userId,
        @RequestBody KeywordRequestDto keywords) {
        userCommandService.addKeywords(userId, keywords);
        return success("keywords 등록 성공");
    }

    @Operation(summary = "회원 키워드 수정 및 삭제 API - 빈 배열 요청하면 삭제")
    @PatchMapping
    public ApiResult<?> updateUserKeyword(@AuthID Integer userId,
        @RequestBody KeywordRequestDto keywords) {
        userCommandService.updateUserKeyword(userId, keywords);
        return success("keywords 수정 성공");
    }

    @Operation(summary = "회원 키워드 조회 API")
    @GetMapping
    public ApiResult<KeywordResponseDto> addUserKeyword(@AuthID Integer authId) {
        KeywordResponseDto userKeywords = userQueryService.findUserKeywords(authId);
        return success(userKeywords);
    }
}
