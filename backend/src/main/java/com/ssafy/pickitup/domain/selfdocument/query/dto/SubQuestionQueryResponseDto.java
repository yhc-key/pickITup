package com.ssafy.pickitup.domain.selfdocument.query.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SubQuestionQueryResponseDto {

    private Integer id;
    private String title;
    private String content;
    private String company;
}
