package com.ssafy.pickitup.domain.badge.api;

import com.ssafy.pickitup.domain.badge.command.BadgeCommandService;
import com.ssafy.pickitup.domain.user.entity.User;
import com.ssafy.pickitup.domain.user.exception.UserNotFoundException;
import com.ssafy.pickitup.domain.user.query.UserQueryJpaRepository;
import com.ssafy.pickitup.global.annotation.AuthID;
import com.ssafy.pickitup.global.api.ApiUtils.ApiResult;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.ssafy.pickitup.global.api.ApiUtils.success;

@Slf4j
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = {"https://pickitup.online", "http://localhost:3000",
    "http://localhost:8080", "https://spring.pickitup.online"}, exposedHeaders = "*")
@RequestMapping("/badge")
public class BadgeController {

    private final BadgeCommandService badgeCommandService;
    private final UserQueryJpaRepository userQueryJpaRepository;

    @Operation(summary = "Badge Test")
    @PostMapping("/test")
    public ApiResult<?> test(@AuthID Integer userId) {
        User user = userQueryJpaRepository.findById(userId)
            .orElseThrow(UserNotFoundException::new);
        badgeCommandService.initBadge(user);
        return success(badgeCommandService.renewBadge(userId));
    }
}
