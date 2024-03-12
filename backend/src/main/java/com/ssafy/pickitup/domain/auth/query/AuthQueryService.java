package com.ssafy.pickitup.domain.auth.query;

import com.ssafy.pickitup.domain.auth.entity.Auth;
import com.ssafy.pickitup.domain.auth.query.dto.AuthDto;
import com.ssafy.pickitup.domain.user.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthQueryService {

    private final AuthQueryJpaRepository authQueryJpaRepository;

    public AuthDto getUserById(int id) {
        Auth auth = authQueryJpaRepository.findAuthById(id);
        if (auth == null) {
            throw new UserNotFoundException("해당 유저를 찾을 수 없습니다");
        }
        return AuthDto.toDto(auth);
    }

}
