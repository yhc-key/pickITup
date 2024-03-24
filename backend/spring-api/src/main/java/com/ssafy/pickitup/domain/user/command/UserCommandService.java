package com.ssafy.pickitup.domain.user.command;

import com.ssafy.pickitup.domain.auth.command.dto.UserSignupDto;
import com.ssafy.pickitup.domain.auth.entity.Auth;
import com.ssafy.pickitup.domain.user.entity.User;
import com.ssafy.pickitup.domain.user.query.dto.KeywordRequestDto;
import com.ssafy.pickitup.domain.user.query.dto.UserResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserCommandService {

    private final UserCommandJpaRepository userCommandJpaRepository;
    private final UserKeywordCommandJpaRepository userKeywordCommandJpaRepository;

    @Transactional
    public UserResponseDto create(Auth auth, UserSignupDto userSignupDto) {
        User user = User.builder()
            .nickname(userSignupDto.getNickname())
            .auth(auth)
            .build();
        System.out.println("user.toString() = " + user.toString());
        userCommandJpaRepository.save(user);
        return UserResponseDto.toDto(user);
    }

    @Transactional
    public UserResponseDto create(Auth auth) {
        User user = User.builder()
            .nickname(auth.getName())
            .auth(auth)
            .build();
        userCommandJpaRepository.save(user);
        return UserResponseDto.toDto(user);
    }

    @Transactional
    public void changeNickname(Integer authId, String nickname) {
        User user = userCommandJpaRepository.findByAuthId(authId);
        user.changeNickname(nickname);
    }

    @Transactional
    public void addKeywords(Integer authId, KeywordRequestDto keywordRequestDto) {
        List<Integer> keywords = keywordRequestDto.getKeywords();
        User user = userCommandJpaRepository.findByAuthId(authId);
        userKeywordCommandJpaRepository.saveUserAndKeywords(authId, keywords);

    }
}
