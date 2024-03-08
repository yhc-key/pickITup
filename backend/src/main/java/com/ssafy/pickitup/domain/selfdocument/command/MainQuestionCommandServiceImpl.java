package com.ssafy.pickitup.domain.selfdocument.command;

import com.ssafy.pickitup.domain.selfdocument.command.dto.MainQuestionCommandRequestDto;
import com.ssafy.pickitup.domain.selfdocument.command.dto.MainQuestionCommandResponseDto;
import com.ssafy.pickitup.domain.selfdocument.entity.MainQuestion;
import com.ssafy.pickitup.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MainQuestionCommandServiceImpl implements MainQuestionCommandService {

    MainQuestionCommandJpaRepository mainQuestionCommandJpaRepository;

    @Override
    public MainQuestionCommandResponseDto registerMain(MainQuestionCommandRequestDto dto,
        User user) {
        MainQuestion mainQuestion = dto.toEntity(user);

        return mainQuestionCommandJpaRepository.save(mainQuestion).toMainQuestionCommandResponse();
    }
}
