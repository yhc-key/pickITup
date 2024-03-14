package com.ssafy.pickitup.domain.selfdocument.command.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@Builder
public class SubQuestionCommandResponseDto {

    private Integer id;
    private String title;
    private String content;
    private String company;
}
