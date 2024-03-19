package com.ssafy.pickitup.domain.auth.query.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthProfileDto {

    private String username;
    private String name;
    private String email;

    public static AuthProfileDto authInfoFromAuthDto(AuthDto authDto) {
        return AuthProfileDto.builder()
            .username(authDto.getUsername())
            .name(authDto.getName())
            .email(authDto.getEmail())
            .build();
    }

}
