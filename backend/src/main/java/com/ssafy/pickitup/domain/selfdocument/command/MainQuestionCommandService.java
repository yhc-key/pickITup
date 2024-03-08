package com.ssafy.pickitup.domain.selfdocument.command;

import com.ssafy.pickitup.domain.selfdocument.command.dto.MainQuestionCommandRequestDto;
import com.ssafy.pickitup.domain.selfdocument.command.dto.MainQuestionCommandResponseDto;
import com.ssafy.pickitup.domain.user.entity.User;

public interface MainQuestionCommandService {

    public MainQuestionCommandResponseDto registerMain(MainQuestionCommandRequestDto dto,
        User user);
}
