package com.ssafy.pickitup.domain.user.dto;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class InterviewWithAnswerDto {

  private Integer interviewId;
  private String mainCategory;
  private String subCategory;
  private String question;
  private String example;
  private String answer;
  private LocalDateTime lastModifiedDate;
}
