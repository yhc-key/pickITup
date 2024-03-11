package com.ssafy.pickitup.domain.auth.query;

import com.ssafy.pickitup.domain.auth.command.dto.LoginRequestDto;
import com.ssafy.pickitup.domain.auth.entity.Auth;
import com.ssafy.pickitup.domain.auth.query.dto.AuthResponseDto;
import com.ssafy.pickitup.domain.user.entity.User;
import com.ssafy.pickitup.domain.user.exception.UserNotFoundException;
import com.ssafy.pickitup.domain.user.query.dto.UserResponseDto;
import javax.security.auth.login.LoginException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthQueryService {

    private final AuthQueryJpaRepository authQueryJpaRepository;

    public AuthResponseDto getUserById(int id) {
        Auth auth = authQueryJpaRepository.findAuthById(id);
        if (auth == null) {
            throw new UserNotFoundException("해당 유저를 찾을 수 없습니다");
        }
        return AuthResponseDto.toDto(auth);
    }
    public AuthResponseDto login(LoginRequestDto loginRequestDto){
        Auth auth = authQueryJpaRepository.findAuthByUsername(loginRequestDto.getUsername());
        if (auth == null) {
            throw new UserNotFoundException("해당 유저를 찾을 수 없습니다");
        }

        if(!auth.getPassword().equals(loginRequestDto.getPassword())){
            throw new UserNotFoundException("비밀번호가 틀렸습니다.");
        }
        return AuthResponseDto.toDto(auth);
    }
}
