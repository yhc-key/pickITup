package com.ssafy.pickitup.domain.user.query.dto;

import java.util.List;
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
public class UserMongoQueryResponseDto {

    private Integer id;

    private List<String> keywords;
    private double latitude;
    private double longitude;

}
