package com.ssafy.pickitup.domain.auth.api;

import com.ssafy.pickitup.domain.auth.command.AuthCommandService;
import com.ssafy.pickitup.domain.auth.command.dto.UserSignupDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthCommandService authCommandService;

    @PostMapping
    public void signup(@RequestBody UserSignupDto userSignupDto) {
        authCommandService.signup(userSignupDto);
    }


}
