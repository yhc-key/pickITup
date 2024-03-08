package com.ssafy.pickitup.domain.selfdocument.query;

import com.ssafy.pickitup.domain.selfdocument.entity.MainQuestion;
import com.ssafy.pickitup.domain.selfdocument.query.dto.MainQuestionQueryResponseDto;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MainQuestionQueryServiceImpl implements MainQuestionQueryService {

    private final MainQuestionQueryJpaRepository mainQuestionQueryJpaRepository;

    @Override
    public List<MainQuestionQueryResponseDto> searchMainQuestions(Integer userId) {
        List<MainQuestionQueryResponseDto> mainQuestionQueryResponseDtos = new ArrayList<>();
        List<MainQuestion> mainQuestions = mainQuestionQueryJpaRepository.findByUserId(userId);
        for (MainQuestion mainQuestion : mainQuestions) {
            MainQuestionQueryResponseDto mainQuestionQueryResponseDto = mainQuestion.toMainQuestionQueryResponse();
            mainQuestionQueryResponseDtos.add(mainQuestionQueryResponseDto);
        }
        return mainQuestionQueryResponseDtos;
    }
}
