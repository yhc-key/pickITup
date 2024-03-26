package com.ssafy.pickitup.domain.interview.api;

import com.ssafy.pickitup.domain.interview.query.dto.InterviewGameDto;
import com.ssafy.pickitup.domain.interview.query.service.InterviewService;
import com.ssafy.pickitup.global.annotation.AuthID;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/interviews")
public class InterviewController {

  private final InterviewService interviewService;

  @GetMapping("/random")
  public List<InterviewGameDto> randomInterviews(String subCategory) {
    return interviewService.findInterviewsBySubCategoryInRandomOrder(subCategory);
  }

  @GetMapping("/test")
  public String test(@AuthID Integer userId) {
    return "test " + userId;
  }
}
