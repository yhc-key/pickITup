package com.ssafy.pickitup.domain.auth.query.dto;

import com.ssafy.pickitup.domain.auth.entity.Auth;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthResponseDto {

    private int id;
    private String username;
    private String name;

    public static AuthResponseDto toDto(Auth auth) {
        return new AuthResponseDto(
            auth.getId(),
            auth.getUsername(),
            auth.getName()
        );
    }

}
