package com.ssafy.pickitup.domain.auth.query.dto;

import com.ssafy.pickitup.domain.auth.entity.Auth;
import com.ssafy.pickitup.domain.auth.entity.Role;
import com.ssafy.pickitup.domain.user.entity.User;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class AuthDto {

    private Integer id;
    private String username;
    private String password;
    private String name;
    private String email;
    private String provider;
    private String providerId;
    private Role role = Role.USER;
    private String refreshToken;
    private User user;

    public static AuthDto toDto(Auth auth) {
        return AuthDto.builder()
            .id(auth.getId())
            .username(auth.getUsername())
            .password(auth.getPassword())
            .name(auth.getName())
            .email(auth.getEmail())
            .provider(auth.getProvider())
            .providerId(auth.getProviderId())
            .role(auth.getRole())
            .refreshToken(auth.getRefreshToken())
            .user(auth.getUser())
            .build();

    }

}
