package com.ssafy.pickitup.domain.user.dto;

import lombok.Data;

@Data
public class UserUpdateRequestDto {

    private String github;
    private String techBlog;
    private String email;
}
