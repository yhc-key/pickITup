package com.ssafy.pickitup.domain.user.query.dto;

import com.ssafy.pickitup.domain.user.entity.User;
import com.ssafy.pickitup.domain.user.entity.UserLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class UserResponseDto {

    private Integer id;
    private String nickname;
    private String github;
    private String techBlog;
    private String address;
    private Integer prevExp;
    private Integer nextExp;
    private Integer exp;
    private Integer level;
    private Integer attendCount;
    private String email;
    private Integer totalMyScrap;
    private Integer totalMyBadge;
    private Integer closingScrap;
    private Integer solvedInterviewCount;

    public static UserResponseDto toDto(User user, int scrapCount, int badgeCount,
        int closingCount, int solvedInterviewCount) {
        return UserResponseDto.builder()
            .id(user.getId())
            .nickname(user.getNickname())
            .github(user.getGithub())
            .techBlog(user.getTechBlog())
            .address(user.getAddress())
            .exp(user.getExp())
            .level(user.getLevel())
            .attendCount(user.getAttendCount())
            .email(user.getAuth().getEmail())
            .totalMyScrap(scrapCount)
            .totalMyBadge(badgeCount)
            .closingScrap(closingCount)
            .solvedInterviewCount(solvedInterviewCount)
            .build();
    }

    public static UserResponseDto toDto(User user, UserLevel userLevel, int scrapCount,
        int badgeCount,
        int closingCount, int solvedInterviewCount) {
        return UserResponseDto.builder()
            .id(user.getId())
            .nickname(user.getNickname())
            .github(user.getGithub())
            .techBlog(user.getTechBlog())
            .address(user.getAddress())
            .prevExp(userLevel.getPrevExp())
            .nextExp(userLevel.getNextExp())
            .exp(user.getExp())
            .level(user.getLevel())
            .attendCount(user.getAttendCount())
            .email(user.getAuth().getEmail())
            .totalMyScrap(scrapCount)
            .totalMyBadge(badgeCount)
            .closingScrap(closingCount)
            .solvedInterviewCount(solvedInterviewCount)
            .build();
    }

}
