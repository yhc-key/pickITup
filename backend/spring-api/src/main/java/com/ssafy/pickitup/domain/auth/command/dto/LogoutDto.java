package com.ssafy.pickitup.domain.auth.command.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LogoutDto {
    private String username;
}
