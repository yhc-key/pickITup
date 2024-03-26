package com.ssafy.pickitup.domain.user.repository;

import com.ssafy.pickitup.domain.user.entity.UserInterview;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
public class CustomUserInterviewRepositoryImpl implements CustomUserInterviewRepository {

  @PersistenceContext
  private EntityManager em;

  @Override
  public void createOrUpdateUserInterview(Integer userId, Integer interviewId, String answer) {


  }
}
