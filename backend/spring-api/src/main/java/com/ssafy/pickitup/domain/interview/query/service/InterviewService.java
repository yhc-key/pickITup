package com.ssafy.pickitup.domain.interview.query.service;

import com.ssafy.pickitup.domain.interview.query.dto.InterviewGameDto;
import java.util.List;

public interface InterviewService {
  List<InterviewGameDto> findInterviewsBySubCategoryInRandomOrder(String subCategory);
}
