package com.ssafy.pickitup.user.controller;

import com.ssafy.pickitup.user.command.UserCommandService;
import com.ssafy.pickitup.user.command.dto.UserSignupDto;
import com.ssafy.pickitup.user.query.UserQueryService;
import com.ssafy.pickitup.user.query.dto.UserResponseDto;
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
public class UserRestController {

  private final UserCommandService userCommandService;
  private final UserQueryService userQueryService;

  @PostMapping
  public void signup(@RequestBody UserSignupDto userSignupDto) {
    userCommandService.signup(userSignupDto);
  }

  @GetMapping("/{id}")
  public UserResponseDto getUser(@PathVariable int id) {
    return userQueryService.getUserById(id);
  }
}
