package com.ssafy.pickitup.domain.selfdocument.query;

import com.ssafy.pickitup.domain.selfdocument.entity.MainQuestion;
import com.ssafy.pickitup.domain.selfdocument.query.dto.MainQuestionQueryResponseDto;
import java.util.List;

public interface MainQuestionQueryService {

    List<MainQuestionQueryResponseDto> searchMainQuestions(Integer userId);

    MainQuestion searchById(Integer mainId);
}
