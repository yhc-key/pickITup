package com.ssafy.pickitup.domain.user.command.service;

public interface UserInterviewCommandService {

  void createOrUpdateUserInterview(Integer userId, Integer interviewId, String answer);
}
