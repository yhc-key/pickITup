package com.ssafy.pickitup.domain.user.query.dto;

import com.ssafy.pickitup.domain.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserResponseDto {

    private int id;
    private String name;

    public static UserResponseDto toDto(User user) {
        return new UserResponseDto(
            user.getId(),
            user.getName()
        );
    }

}
