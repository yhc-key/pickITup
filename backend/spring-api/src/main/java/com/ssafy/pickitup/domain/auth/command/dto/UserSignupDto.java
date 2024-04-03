package com.ssafy.pickitup.domain.auth.command.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class UserSignupDto {

    private String username;
    private String password;
    private String name;
    private String nickname;
    private String email;
    private String address;
}
