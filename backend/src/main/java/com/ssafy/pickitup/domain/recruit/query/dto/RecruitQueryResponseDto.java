package com.ssafy.pickitup.domain.recruit.query.dto;

import java.util.Set;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class RecruitQueryResponseDto {

    private Integer id;
    private String source;
    private String title;
    private String company;
    private String url;
    private String thumbnailUrl;
    private Set<String> qualificationRequirements;
    private Set<String> preferredRequirements;
    private String dueDate;
    private String career;
}
