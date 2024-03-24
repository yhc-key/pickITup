package com.ssafy.pickitup.domain.user.api;

import static com.ssafy.pickitup.domain.auth.api.ApiUtils.success;

import com.ssafy.pickitup.domain.auth.api.ApiUtils.ApiResult;
import com.ssafy.pickitup.domain.recruit.query.RecruitQueryService;
import com.ssafy.pickitup.domain.recruit.query.dto.RecruitQueryResponseDto;
import com.ssafy.pickitup.domain.user.command.UserCommandService;
import com.ssafy.pickitup.domain.user.entity.Keyword;
import com.ssafy.pickitup.domain.user.query.UserQueryService;
import com.ssafy.pickitup.domain.user.query.dto.KeywordRequestDto;
import com.ssafy.pickitup.domain.user.query.dto.KeywordResponseDto;
import com.ssafy.pickitup.domain.user.query.dto.NicknameDto;
import com.ssafy.pickitup.domain.user.query.dto.UserResponseDto;
import com.ssafy.pickitup.security.jwt.JwtTokenProvider;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = {"https://pickitup.online", "http://localhost:3000", "http://localhost:8080",
    "https://spring.pickitup.online"}, exposedHeaders = "*")
@RequestMapping("/users")
public class UserController {

    private final UserCommandService userCommandService;
    private final UserQueryService userQueryService;
    private final RecruitQueryService recruitQueryService;
    private final JwtTokenProvider jwtTokenProvider;

    @Operation(summary = "회원 정보 조회 API")
    @GetMapping("/me")
    public ApiResult<UserResponseDto> getUser(
        @RequestHeader(HttpHeaders.AUTHORIZATION) String accessToken) {
        int authId = Integer.valueOf(jwtTokenProvider.extractAuthId(accessToken));
        log.info("authId = {}", authId);
        return success(userQueryService.getUserById(authId));
    }

    @Operation(summary = "닉네임 변경 API")
    @PatchMapping("/nickname")
    public ApiResult<?> changeNickname(HttpServletRequest request,
        @RequestBody NicknameDto nickname) {
        String accessToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        int authId = Integer.valueOf(jwtTokenProvider.extractAuthId(accessToken));
        log.info("authId = {}", authId);
        userCommandService.changeNickname(authId, nickname.getNickname());
        return success("닉네임 변경 성공");
    }

    @Operation(summary = "회원 스크랩 채용 공고 조회 API")
    @GetMapping("/{authId}/scraps/recruit")
    public ApiResult<Page<RecruitQueryResponseDto>> getUserScrapList(
        @PathVariable("authId") int authId, Pageable pageable) {
        Page<RecruitQueryResponseDto> myRecruitByIdList = userQueryService.findMyRecruitById(authId,
            pageable);
        return success(myRecruitByIdList);
//        return succss(ReposnseList);
    }

    @Operation(summary = "회원 키워드 추가 API")
    @PostMapping("/keywords")
    public ApiResult<?> addUserKeyword(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String accessToken, @RequestBody KeywordRequestDto keywords) {
        int authId = Integer.valueOf(jwtTokenProvider.extractAuthId(accessToken));
        log.info("keywords = {}", keywords.toString());
        userCommandService.addKeywords(authId, keywords);
        return success("keywords 등록 성공");
    }

    @Operation(summary = "회원 키워드 조회 API")
    @GetMapping("{authId}/keywords")
    public ApiResult<KeywordResponseDto> addUserKeyword(
            @PathVariable("authId") Integer authId) {
        KeywordResponseDto userKeywords = userQueryService.findUserKeywords(authId);
        return success(userKeywords);
    }


}
