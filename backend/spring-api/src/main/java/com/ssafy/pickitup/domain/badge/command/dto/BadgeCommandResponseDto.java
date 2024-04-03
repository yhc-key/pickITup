package com.ssafy.pickitup.domain.badge.command.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BadgeCommandResponseDto {

    List<String> badges;
}
