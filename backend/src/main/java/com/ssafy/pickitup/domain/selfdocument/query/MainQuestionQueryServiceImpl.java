package com.ssafy.pickitup.domain.selfdocument.query;

import com.ssafy.pickitup.domain.selfdocument.entity.MainQuestion;
import com.ssafy.pickitup.domain.selfdocument.exception.MainQuestionNotFoundException;
import com.ssafy.pickitup.domain.selfdocument.query.dto.MainQuestionQueryResponseDto;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MainQuestionQueryServiceImpl implements MainQuestionQueryService {

    private final MainQuestionQueryJpaRepository mainRepository;

    @Override
    public List<MainQuestionQueryResponseDto> searchMainQuestions(Integer userId) {
        List<MainQuestionQueryResponseDto> responseDtoList = new ArrayList<>();
        List<MainQuestion> mainQuestions = mainRepository.findByUserId(userId);
        for (MainQuestion mainQuestion : mainQuestions) {
            MainQuestionQueryResponseDto responseDto = mainQuestion.toQueryResponse();
            responseDtoList.add(responseDto);
        }
        return responseDtoList;
    }

    @Override
    public MainQuestion searchById(Integer mainId) {
        return mainRepository.findById(mainId)
            .orElseThrow(MainQuestionNotFoundException::new);
    }
}
