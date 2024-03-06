package com.ssafy.pickitup.user.query;

import com.ssafy.pickitup.user.entity.User;
import com.ssafy.pickitup.user.exception.UserNotFoundException;
import com.ssafy.pickitup.user.query.dto.UserResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserQueryService {

    private final UserQueryJpaRepository userQueryJpaRepository;

    public UserResponseDto getUserById(int id) {
        User user = userQueryJpaRepository.findById(id);
        if(user == null) throw new UserNotFoundException("해당 유저를 찾을 수 없습니다");
        //Slave DB에 CUD 작업을 하면 에러 발생
//        User newUser = User.builder().name("에러유저").build();
//        userQueryJpaRepository.save(newUser);
        return UserResponseDto.toDto(user);
    }

}
