package com.ssafy.pickitup.domain.selfdocument.command;


import com.ssafy.pickitup.domain.selfdocument.command.dto.SubQuestionCommandRequestDto;
import com.ssafy.pickitup.domain.selfdocument.command.dto.SubQuestionCommandResponseDto;

public interface SubQuestionCommandService {

    SubQuestionCommandResponseDto registerSubQuestion(Integer mainId,
        SubQuestionCommandRequestDto dto);

    boolean deleteSubQuestion(Integer subId);

    SubQuestionCommandResponseDto modifySubQuestion(Integer subId,
        SubQuestionCommandRequestDto dto);
}
