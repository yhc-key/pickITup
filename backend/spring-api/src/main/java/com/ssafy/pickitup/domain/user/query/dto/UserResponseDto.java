package com.ssafy.pickitup.domain.user.query.dto;

import com.ssafy.pickitup.domain.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserResponseDto {

    private Integer id;
    private String nickname;
    private String github;
    private String techBlog;
    private Integer exp;
    private Integer level;
    private Integer attendCount;
    private Integer totalMyScrap;
    private Integer totalMyBadge;
    private Integer closingScrap;

    public static UserResponseDto toDto(User user, int scrapCount, int badgeCount,
        int closingCount) {
        return new UserResponseDto(
            user.getId(),
            user.getNickname(),
            user.getGithub(),
            user.getTechBlog(),
            user.getExp(),
            user.getLevel(),
            user.getAttendCount(),
            scrapCount,
            badgeCount,
            closingCount
        );
    }

}
