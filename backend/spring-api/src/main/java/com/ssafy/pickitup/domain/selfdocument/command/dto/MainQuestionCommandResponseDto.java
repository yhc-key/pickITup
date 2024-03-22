package com.ssafy.pickitup.domain.selfdocument.command.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MainQuestionCommandResponseDto {

    private Integer id;
    private String title;
}
