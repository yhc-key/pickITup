package com.ssafy.pickitup.domain.selfdocument.query;

import com.ssafy.pickitup.domain.selfdocument.entity.SubQuestion;
import com.ssafy.pickitup.domain.selfdocument.query.dto.SubQuestionQueryResponseDto;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class SubQuestionQueryServiceImpl implements SubQuestionQueryService {

    private final SubQuestionQueryJpaRepository subRepository;

    @Override
    public List<SubQuestionQueryResponseDto> searchSubQuestions(Integer mainId) {
        List<SubQuestionQueryResponseDto> responseDtoList = new ArrayList<>();
        List<SubQuestion> subQuestions = subRepository.findByMainQuestionId(mainId);
        for (SubQuestion subQuestion : subQuestions) {
            SubQuestionQueryResponseDto responseDto = subQuestion.toQueryResponse();
            responseDtoList.add(responseDto);
        }
        return responseDtoList;
    }
}
