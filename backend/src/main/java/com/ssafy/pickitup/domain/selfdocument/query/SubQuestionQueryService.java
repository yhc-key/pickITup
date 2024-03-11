package com.ssafy.pickitup.domain.selfdocument.query;

import com.ssafy.pickitup.domain.selfdocument.query.dto.SubQuestionQueryResponseDto;
import java.util.List;

public interface SubQuestionQueryService {

    List<SubQuestionQueryResponseDto> searchSubQuestions(Integer mainId);
}
