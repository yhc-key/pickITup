package com.ssafy.pickitup.domain.interview.query.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class InterviewGameDto {

  private Integer id;
  private String question;
}
