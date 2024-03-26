package com.ssafy.pickitup.domain.user.query.service;

import com.ssafy.pickitup.domain.user.entity.UserInterview;
import com.ssafy.pickitup.domain.user.repository.UserInterviewRepository;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserInterviewQueryServiceImpl implements UserInterviewQueryService {

  private final UserInterviewRepository userInterviewRepository;

  @Override
  public void findInterviewsAndAnswersByUserId(Integer userId) {
    Stream<UserInterview> userInterviews = userInterviewRepository.findByUserIdOrderByLastModifiedDateDesc(userId);
  }
}
