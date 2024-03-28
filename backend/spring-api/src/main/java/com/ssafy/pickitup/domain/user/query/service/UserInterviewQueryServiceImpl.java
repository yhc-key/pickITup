package com.ssafy.pickitup.domain.user.query.service;

import com.ssafy.pickitup.domain.user.dto.InterviewWithAnswerDto;
import com.ssafy.pickitup.domain.user.entity.UserInterview;
import com.ssafy.pickitup.domain.user.repository.UserInterviewRepository;
import java.util.stream.Stream;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserInterviewQueryServiceImpl implements UserInterviewQueryService {

  private final UserInterviewRepository userInterviewRepository;

  @Override
  @Transactional(readOnly = true)
  public List<InterviewWithAnswerDto> findInterviewsAndAnswersByUserId(Integer userId) {
    Stream<UserInterview> userInterviews = userInterviewRepository.findByUserIdOrderByLastModifiedDateDesc(userId);
    return userInterviews.map(userInterview -> InterviewWithAnswerDto.builder()
        .interviewId(userInterview.getInterview().getId())
        .mainCategory(userInterview.getInterview().getMainCategory())
        .subCategory(userInterview.getInterview().getSubCategory())
        .question(userInterview.getInterview().getQuestion())
        .example(userInterview.getInterview().getExample())
        .answer(userInterview.getAnswer())
        .lastModifiedDate(userInterview.getLastModifiedDate())
        .build())
        .toList();
  }

  @Override
  public int countSolvedInterviewsByUserId(Integer userId) {
    return userInterviewRepository.countByUserId(userId);
  }
}
