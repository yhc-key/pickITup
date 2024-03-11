package com.ssafy.pickitup.domain.selfdocument.query;

import com.ssafy.pickitup.domain.selfdocument.entity.SubQuestion;
import com.ssafy.pickitup.domain.selfdocument.query.dto.SubQuestionQueryResponseDto;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class SubQuestionQueryServiceImpl implements SubQuestionQueryService {

    private final SubQuestionQueryJpaRepository subRepository;

    @Override
    public List<SubQuestionQueryResponseDto> searchSubQuestions(Integer mainId) {
        return subRepository.findByMainQuestionId(mainId)
            .stream()
            .map(SubQuestion::toQueryResponse)
            .collect(Collectors.toList());
    }
}
