package com.ssafy.pickitup.domain.recruit.query.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RecruitQueryRequestDto {

    private List<String> keywords;
    private String query;
}
