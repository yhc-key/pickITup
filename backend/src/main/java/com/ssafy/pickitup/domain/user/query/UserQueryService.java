package com.ssafy.pickitup.domain.user.query;

import com.ssafy.pickitup.domain.user.entity.User;
import com.ssafy.pickitup.domain.user.exception.UserNotFoundException;
import com.ssafy.pickitup.domain.user.query.dto.UserResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserQueryService {

    private final UserQueryJpaRepository userQueryJpaRepository;

    public UserResponseDto getUserById(int id) {
        User user = userQueryJpaRepository.findById(id);
        if (user == null) {
            throw new UserNotFoundException("해당 유저를 찾을 수 없습니다");
        }

        return UserResponseDto.toDto(user);
    }
}
