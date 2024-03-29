package com.ssafy.pickitup.domain.user.api;

import static com.ssafy.pickitup.global.api.ApiUtils.success;

import com.ssafy.pickitup.domain.user.command.service.UserClickService;
import com.ssafy.pickitup.domain.user.command.service.UserCommandService;
import com.ssafy.pickitup.domain.user.query.dto.UserClickResponseDto;
import com.ssafy.pickitup.global.annotation.AuthID;
import com.ssafy.pickitup.global.api.ApiUtils.ApiResult;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = {"https://pickitup.online", "http://localhost:3000",
    "http://localhost:8080", "https://spring.pickitup.online"}, exposedHeaders = "*")
@RequestMapping("/users/click")
public class UserClickController {

    private final UserCommandService userCommandService;
    private final UserClickService userClickService;

    @Operation(summary = "회원 채용 공고 클릭 API")
    @PostMapping("/recruit")
    public ApiResult<?> clickRecruit(@AuthID Integer userId, @RequestParam Integer recruitId) {
        userCommandService.increaseUserClick(userId, recruitId);
        return success("채용 공고 클릭");
    }

    @Operation(summary = "회원 클릭 데이터 조회 API - 서버 테스트용")
    @GetMapping("/recruit")
    public ApiResult<?> getUserClick(@AuthID Integer authId) {
        UserClickResponseDto allUserClick = userClickService.findAllUserClick(authId);
        return success(allUserClick);
    }
}
