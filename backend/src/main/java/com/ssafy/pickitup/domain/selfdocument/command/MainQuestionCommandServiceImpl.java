package com.ssafy.pickitup.domain.selfdocument.command;

import com.ssafy.pickitup.domain.selfdocument.command.dto.MainQuestionCommandRequestDto;
import com.ssafy.pickitup.domain.selfdocument.command.dto.MainQuestionCommandResponseDto;
import com.ssafy.pickitup.domain.selfdocument.entity.MainQuestion;
import com.ssafy.pickitup.domain.selfdocument.exception.MainQuestionNotFoundException;
import com.ssafy.pickitup.domain.selfdocument.query.MainQuestionQueryJpaRepository;
import com.ssafy.pickitup.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MainQuestionCommandServiceImpl implements MainQuestionCommandService {

    MainQuestionCommandJpaRepository mainQuestionCommandJpaRepository;
    MainQuestionQueryJpaRepository mainQuestionQueryJpaRepository;

    @Override
    public MainQuestionCommandResponseDto registerMainQuestion(MainQuestionCommandRequestDto dto,
        User user) {
        MainQuestion mainQuestion = dto.toEntity(user);

        return mainQuestionCommandJpaRepository.save(mainQuestion).toMainQuestionCommandResponse();
    }

    @Override
    public boolean deleteMainQuestion(Integer mainId) {
        try {
            MainQuestion mainQuestion = mainQuestionQueryJpaRepository.findById(mainId)
                .orElseThrow(MainQuestionNotFoundException::new);

            mainQuestionCommandJpaRepository.delete(mainQuestion);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public MainQuestionCommandResponseDto updateMainQuestion(Integer id,
        MainQuestionCommandRequestDto dto) {
        MainQuestion mainQuestion = mainQuestionQueryJpaRepository.findById(id)
            .orElseThrow(MainQuestionNotFoundException::new);
        mainQuestion.setTitle(dto.getTitle());
        return mainQuestionCommandJpaRepository.save(mainQuestion)
            .toMainQuestionCommandResponse();
    }
}
