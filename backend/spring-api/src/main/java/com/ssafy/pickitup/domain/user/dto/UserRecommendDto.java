package com.ssafy.pickitup.domain.user.dto;

import java.util.List;
import lombok.Data;

@Data
public class UserRecommendDto {

    private String recruitId;
    private String company;
    private List<String> intersection;
    private double distance;
}
