package com.ssafy.pickitup.domain.selfdocument.command.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@Builder
public class MainQuestionCommandResponseDto {

    private Integer id;
    private String title;
}
