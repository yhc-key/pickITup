package com.ssafy.pickitup.domain.interview.query.service;

import com.ssafy.pickitup.domain.interview.entity.Interview;
import com.ssafy.pickitup.domain.interview.query.dto.InterviewGameDto;
import com.ssafy.pickitup.domain.interview.repository.InterviewRepository;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class InterviewServiceImpl implements InterviewService {

  private final InterviewRepository interviewRepository;

  @Override
  @Transactional(readOnly = true)
  public List<InterviewGameDto> findInterviewsBySubCategoryInRandomOrder(String subCategory) {

    Stream<Interview> interviews = interviewRepository.findBySubCategory(subCategory);

    List<InterviewGameDto> interviewGameDtos = new ArrayList<>(
        interviews.map(interview -> InterviewGameDto.builder()
                .id(interview.getId())
                .question(interview.getQuestion())
                .build())
            .toList());
    Collections.shuffle(interviewGameDtos);
    return interviewGameDtos;
  }
}
