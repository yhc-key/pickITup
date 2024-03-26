package com.ssafy.pickitup.domain.user.query.dto;

import com.ssafy.pickitup.domain.user.entity.User;
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
public class UserDto {

    private Integer recruitViewCount;
    private Integer recruitScrapCount;
    private Integer selfAnswerCount;
    private Integer gameWinCount;
    private Integer attendCount;
    private Integer exp;
    private Integer level;

    public static UserDto toDto(User user) {
        return new UserDto(
            user.getRecruitViewCount(),
            user.getRecruitScrapCount(),
            user.getSelfAnswerCount(),
            user.getGameWinCount(),
            user.getAttendCount(),
            user.getExp(),
            user.getLevel()
        );
    }

}
