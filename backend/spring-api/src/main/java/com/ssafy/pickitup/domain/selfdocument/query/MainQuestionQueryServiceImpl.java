package com.ssafy.pickitup.domain.selfdocument.query;

import com.ssafy.pickitup.domain.selfdocument.entity.MainQuestion;
import com.ssafy.pickitup.domain.selfdocument.exception.MainQuestionNotFoundException;
import com.ssafy.pickitup.domain.selfdocument.query.dto.MainQuestionQueryResponseDto;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MainQuestionQueryServiceImpl implements MainQuestionQueryService {

    private final MainQuestionQueryJpaRepository mainRepository;

    @Override
    public List<MainQuestionQueryResponseDto> searchMainQuestions(Integer userId) {
        return mainRepository.findByUserId(userId)
            .stream()
            .map(MainQuestion::toQueryResponse)
            .collect(Collectors.toList());
    }

    @Override
    public MainQuestion searchById(Integer mainId) {
        return mainRepository.findById(mainId)
            .orElseThrow(MainQuestionNotFoundException::new);
    }
}
