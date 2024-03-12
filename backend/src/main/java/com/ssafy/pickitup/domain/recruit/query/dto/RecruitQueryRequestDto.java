package com.ssafy.pickitup.domain.recruit.query.dto;

import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class RecruitQueryRequestDto {

    private List<String> keywords;
    private String query;
    private Integer pageNo = 0;
}
