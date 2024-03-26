package com.ssafy.pickitup.domain.user.repository;

public interface CustomUserInterviewRepository {

  void createOrUpdateUserInterview(Integer userId, Integer interviewId, String answer);
}
