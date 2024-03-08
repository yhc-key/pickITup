package com.ssafy.pickitup.domain.auth.api;

import com.ssafy.pickitup.domain.auth.command.AuthCommandService;
import com.ssafy.pickitup.domain.auth.command.dto.UserSignupDto;
import com.ssafy.pickitup.domain.user.command.UserCommandService;
import com.ssafy.pickitup.domain.user.query.dto.UserResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
@Tag(name = "AuthController", description = "회원 인증 정보 관련 API")
public class AuthController {

    private final AuthCommandService authCommandService;
    private final UserCommandService userCommandService;

    @Operation(summary = "회원 가입 API")
    @PostMapping("/signup")
    public AuthApiResponse<?> signup(@RequestBody UserSignupDto userSignupDto) {
        //auth 정보 저장
        authCommandService.signup(userSignupDto);
        //user 정보 저장
        UserResponseDto userResponseDto = userCommandService.create(userSignupDto);

        return new AuthApiResponse<>(userResponseDto);
    }


}
