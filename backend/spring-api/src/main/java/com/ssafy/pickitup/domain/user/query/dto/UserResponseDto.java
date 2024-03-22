package com.ssafy.pickitup.domain.user.query.dto;

import com.ssafy.pickitup.domain.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserResponseDto {

    private Integer id;
    private String nickname;
    private String profile;
    private String github;
    private String techBlog;

    public static UserResponseDto toDto(User user) {
        return new UserResponseDto(
            user.getId(),
            user.getNickname(),
            user.getProfile(),
            user.getGithub(),
            user.getTechBlog()

        );
    }

}
