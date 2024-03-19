package com.ssafy.pickitup.domain.selfdocument.command;

import com.ssafy.pickitup.domain.selfdocument.command.dto.SubQuestionCommandRequestDto;
import com.ssafy.pickitup.domain.selfdocument.command.dto.SubQuestionCommandResponseDto;
import com.ssafy.pickitup.domain.selfdocument.entity.MainQuestion;
import com.ssafy.pickitup.domain.selfdocument.entity.SubQuestion;
import com.ssafy.pickitup.domain.selfdocument.exception.SubQuestionNotFoundException;
import com.ssafy.pickitup.domain.selfdocument.query.MainQuestionQueryService;
import com.ssafy.pickitup.domain.selfdocument.query.SubQuestionQueryJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class SubQuestionCommandServiceImpl implements SubQuestionCommandService {

    private final MainQuestionQueryService mainQueryService;
    private final MainQuestionCommandJpaRepository mainCommandRepository;
    private final SubQuestionQueryJpaRepository subQueryRepository;
    private final SubQuestionCommandJpaRepository subCommandRepository;

    @Override
    @Transactional
    public SubQuestionCommandResponseDto registerSubQuestion(Integer mainId,
        SubQuestionCommandRequestDto dto) {
        MainQuestion mainQuestion = mainQueryService.searchById(mainId);
        SubQuestion subQuestion = dto.toEntity(mainQuestion);
        SubQuestionCommandResponseDto responseDto = subCommandRepository.save(subQuestion)
            .toCommandResponse();
        mainQuestion.getSubQuestions().add(subQuestion);
        mainCommandRepository.save(mainQuestion);
        return responseDto;
    }

    @Override
    @Transactional
    public boolean deleteSubQuestion(Integer subId) {
        try {
            SubQuestion subQuestion = subQueryRepository.findById(subId)
                .orElseThrow(SubQuestionNotFoundException::new);

            subCommandRepository.delete(subQuestion);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    @Transactional
    public SubQuestionCommandResponseDto modifySubQuestion(Integer subId,
        SubQuestionCommandRequestDto dto) {
        SubQuestion subQuestion = subQueryRepository.findById(subId)
            .orElseThrow(SubQuestionNotFoundException::new);
        subQuestion.setTitle(dto.getTitle());
        subQuestion.setContent(dto.getContent());
        subQuestion.setCompany(dto.getCompany());
        return subCommandRepository.save(subQuestion).toCommandResponse();
    }
}
