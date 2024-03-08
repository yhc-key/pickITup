package com.ssafy.pickitup.domain.selfdocument.command;

import com.ssafy.pickitup.domain.selfdocument.command.dto.MainQuestionCommandRequestDto;
import com.ssafy.pickitup.domain.selfdocument.command.dto.MainQuestionCommandResponseDto;
import com.ssafy.pickitup.domain.user.entity.User;

public interface MainQuestionCommandService {

    MainQuestionCommandResponseDto registerMainQuestion(MainQuestionCommandRequestDto dto,
        User user);

    boolean deleteMainQuestion(Integer mainId);

    MainQuestionCommandResponseDto updateMainQuestion(Integer id,
        MainQuestionCommandRequestDto dto);
}
