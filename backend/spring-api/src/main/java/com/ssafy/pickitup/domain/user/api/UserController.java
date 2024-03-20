package com.ssafy.pickitup.domain.user.api;

import static com.ssafy.pickitup.domain.auth.api.ApiUtils.success;

import com.ssafy.pickitup.domain.auth.api.ApiUtils.ApiResult;
import com.ssafy.pickitup.domain.user.command.UserCommandService;
import com.ssafy.pickitup.domain.user.query.UserQueryService;
import com.ssafy.pickitup.domain.user.query.dto.NicknameDto;
import com.ssafy.pickitup.domain.user.query.dto.UserResponseDto;
import com.ssafy.pickitup.security.jwt.JwtTokenProvider;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = {"https://pickitup.online", "http://localhost:3000", "http://localhost:8080",
    "https://spring.pickitup.online"}, exposedHeaders = "*")
@RequestMapping("/users")
public class UserController {

    private final UserCommandService userCommandService;
    private final UserQueryService userQueryService;
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
        userCommandService.changeNickname(authId, nickname.getNickname());
        return success("닉네임 변경 성공");
    }


}
