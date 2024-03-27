package com.ssafy.pickitup.domain.badge.api;

import static com.ssafy.pickitup.domain.auth.api.ApiUtils.success;

import com.ssafy.pickitup.domain.auth.api.ApiUtils.ApiResult;
import com.ssafy.pickitup.domain.badge.command.BadgeCommandService;
import com.ssafy.pickitup.domain.user.query.UserQueryJpaRepository;
import com.ssafy.pickitup.security.jwt.JwtTokenProvider;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = {"https://pickitup.online", "http://localhost:3000", "http://localhost:8080",
    "https://spring.pickitup.online"}, exposedHeaders = "*")
@RequestMapping("/badge")
public class BadgeController {

    private final JwtTokenProvider jwtTokenProvider;

    private final BadgeCommandService badgeCommandService;
    private final UserQueryJpaRepository userQueryJpaRepository;

    @Operation(summary = "Badge Test")
    @PostMapping("/test")
    public ApiResult<?> test(@RequestHeader(HttpHeaders.AUTHORIZATION) String accessToken) {
        Integer userId = Integer.valueOf(jwtTokenProvider.extractAuthId(accessToken));
        badgeCommandService.initBadge(userId);
        return success(badgeCommandService.renewBadge(userId));
    }
}
