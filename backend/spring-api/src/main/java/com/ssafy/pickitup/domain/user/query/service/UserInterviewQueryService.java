package com.ssafy.pickitup.domain.user.query.service;

import com.ssafy.pickitup.domain.user.dto.InterviewWithAnswerDto;
import java.util.List;

public interface UserInterviewQueryService {

  List<InterviewWithAnswerDto> findInterviewsAndAnswersByUserId(Integer userId);
}
