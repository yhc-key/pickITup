package com.ssafy.pickitup.domain.selfdocument.command;

import com.ssafy.pickitup.domain.selfdocument.command.dto.MainQuestionCommandRequestDto;
import com.ssafy.pickitup.domain.selfdocument.command.dto.MainQuestionCommandResponseDto;

public interface MainQuestionCommandService {

    MainQuestionCommandResponseDto registerMainQuestion(
        Integer userId, MainQuestionCommandRequestDto dto);

    boolean deleteMainQuestion(Integer mainId);

    MainQuestionCommandResponseDto modifyMainQuestion(Integer id,
        MainQuestionCommandRequestDto dto);
}
