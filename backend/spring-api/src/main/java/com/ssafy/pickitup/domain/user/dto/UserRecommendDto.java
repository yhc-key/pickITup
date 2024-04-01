package com.ssafy.pickitup.domain.user.dto;

import java.time.LocalDate;
import java.util.List;
import lombok.Data;

@Data
public class UserRecommendDto {

    private Integer recruitId;
    private String company;
    private String url;
    private List<String> intersection;
    private double distance;
    private List<String> qualificationRequirements;
    private List<String> preferredRequirements;
    private LocalDate dueDate;
    private String title;
}
