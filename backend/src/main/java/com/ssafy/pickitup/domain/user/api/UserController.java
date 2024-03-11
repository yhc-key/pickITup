package com.ssafy.pickitup.domain.user.api;

import com.ssafy.pickitup.domain.user.command.UserCommandService;
import com.ssafy.pickitup.domain.auth.command.dto.UserSignupDto;
import com.ssafy.pickitup.domain.user.query.UserQueryService;
import com.ssafy.pickitup.domain.user.query.dto.UserResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserCommandService userCommandService;
    private final UserQueryService userQueryService;


    @GetMapping("/{id}")
    public UserResponseDto getUser(@PathVariable int id) {
        return userQueryService.getUserById(id);
    }
}
