package com.ssafy.pickitup.domain.selfdocument.query;

import com.ssafy.pickitup.domain.selfdocument.query.dto.MainQuestionQueryResponseDto;
import java.util.List;

public interface MainQuestionQueryService {

    public List<MainQuestionQueryResponseDto> searchMainQuestions(Integer userId);
}
