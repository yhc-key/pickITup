package com.ssafy.pickitup.domain.badge.query.dto;

import com.ssafy.pickitup.domain.badge.entity.Badge;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BadgeQueryResponseDto {

    Badge badge;
    boolean isAchieve;
}
