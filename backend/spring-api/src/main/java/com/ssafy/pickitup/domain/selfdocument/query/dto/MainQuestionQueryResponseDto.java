package com.ssafy.pickitup.domain.selfdocument.query.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class MainQuestionQueryResponseDto {

    private Integer id;
    private String title;
}
