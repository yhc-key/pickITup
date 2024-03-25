package com.ssafy.pickitup.domain.user.query.dto;

import lombok.Data;

import java.util.List;

@Data
public class KeywordResponseDto {
    private List<String> keywords;


    public KeywordResponseDto (List<String> keywords) {
        this.keywords = keywords;
    }
}
