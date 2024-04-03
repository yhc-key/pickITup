package com.ssafy.pickitup.domain.badge.query.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BadgeQueryResponseDto {

    String badgeName;
    boolean isAchieve;
}
