package com.ssafy.pickitup.domain.user.api;

import com.ssafy.pickitup.domain.auth.api.ApiUtils;
import com.ssafy.pickitup.domain.auth.api.ApiUtils.ApiResult;
import com.ssafy.pickitup.domain.user.command.service.UserInterviewCommandService;
import com.ssafy.pickitup.domain.user.dto.InterviewWithAnswerDto;
import com.ssafy.pickitup.domain.user.query.service.UserInterviewQueryService;
import com.ssafy.pickitup.global.annotation.AuthID;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
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

  private final UserInterviewCommandService userInterviewCommandService;
  private final UserInterviewQueryService userInterviewQueryService;

  @GetMapping
  public ApiResult<List<InterviewWithAnswerDto>> getMyInterviewsAndAnswers(@AuthID Integer userId) {
    List<InterviewWithAnswerDto> interviewsAndAnswers = userInterviewQueryService.findInterviewsAndAnswersByUserId(userId);
    return ApiUtils.success(interviewsAndAnswers);
  }

  @PostMapping("/{interviewId}")
  public ApiResult<?> createOrUpdateUserInterview(@PathVariable Integer interviewId,
                                          @AuthID Integer userId,
                                          @RequestBody String answer) {
    userInterviewCommandService.createOrUpdateUserInterview(userId, interviewId, answer);
    return ApiUtils.success(null);
  }
}
