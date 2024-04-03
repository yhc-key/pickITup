package com.ssafy.pickitup.domain.user.query.dto;

import lombok.Data;

import java.util.List;

@Data
public class KeywordRequestDto {
    private List<Integer> keywords;
}
