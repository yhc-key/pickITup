package com.ssafy.pickitup.domain.user.query.dto;

import com.ssafy.pickitup.domain.user.entity.UserClick;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserClickResponseDto {

    private Integer userId;
    private List<int[]> clickData;

    public static UserClickResponseDto toDto(Integer userId, List<UserClick> userClickList) {
        List<int[]> list = userClickList.stream()
            .map(userClick -> new int[]{userClick.getRecruitId(), userClick.getClickCount()})
            .toList();
        return new UserClickResponseDto(userId, list);
    }
}
