package com.ssafy.pickitup.domain.user.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserInterviewRequestDto {

  private Integer interviewId;
  private String answer;
}
