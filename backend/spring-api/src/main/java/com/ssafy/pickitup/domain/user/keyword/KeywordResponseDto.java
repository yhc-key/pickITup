package com.ssafy.pickitup.domain.user.keyword;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class KeywordResponseDto {

    private Integer id;
    private String category;
    private String name;

    public static KeywordResponseDto toDto(Keyword keyword) {
        return new KeywordResponseDto(keyword.getId(), keyword.getCategory(), keyword.getName());
    }
}
