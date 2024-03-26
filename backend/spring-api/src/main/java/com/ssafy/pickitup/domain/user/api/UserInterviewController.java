package com.ssafy.pickitup.domain.user.api;

import com.ssafy.pickitup.domain.user.command.service.UserInterviewCommandService;
import com.ssafy.pickitup.global.annotation.AuthID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/my/interviews")
public class UserInterviewController {

  private final UserInterviewCommandService userInterviewService;

  @PostMapping("/{interviewId}")
  public void createOrUpdateUserInterview(@PathVariable Integer interviewId,
                                          @AuthID Integer userId,
                                          @RequestBody String answer) {
    userInterviewService.createOrUpdateUserInterview(userId, interviewId, answer);
  }
}
