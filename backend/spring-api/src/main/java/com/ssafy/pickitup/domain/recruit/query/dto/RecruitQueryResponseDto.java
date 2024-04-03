package com.ssafy.pickitup.domain.recruit.query.dto;

import java.time.LocalDate;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RecruitQueryResponseDto {

    private Integer id;
    private String source;
    private String title;
    private String company;
    private Integer companyId;
    private String url;
    private String thumbnailUrl;
    private Set<String> qualificationRequirements;
    private Set<String> preferredRequirements;
    private LocalDate dueDate;
    private int[] career;
}
