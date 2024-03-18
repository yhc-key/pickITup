package com.ssafy.pickitup.domain.auth.query;

import com.ssafy.pickitup.domain.auth.entity.Auth;
import com.ssafy.pickitup.domain.auth.query.dto.AuthDto;
import com.ssafy.pickitup.domain.user.exception.UserNotFoundException;
import com.ssafy.pickitup.security.exception.JwtBlackListException;
import com.ssafy.pickitup.security.exception.RefreshTokenException;
import com.ssafy.pickitup.security.jwt.JwtTokenProvider;
import com.ssafy.pickitup.security.service.RedisService;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthQueryService {

    private final RedisService redisService;
    private final JwtTokenProvider jwtTokenProvider;

    private final AuthQueryJpaRepository authQueryJpaRepository;

    public AuthDto getUserById(int id) {
        Auth auth = authQueryJpaRepository.findAuthById(id);
        if (auth == null) {
            throw new UserNotFoundException("해당 유저를 찾을 수 없습니다");
        }
        return AuthDto.getAuth(auth);
    }

    public AuthDto getUserByUsername(String username) {
        Auth auth = authQueryJpaRepository.findAuthByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException(
                "Can't find user with this username. -> " + username));
        return AuthDto.getAuth(auth);
    }

    public void detectConcurrentUser(String requestAccessToken, String requestRefreshToken) {
        String accessToken = jwtTokenProvider.resolveToken(requestAccessToken);
        if (!jwtTokenProvider.validateToken(accessToken)) {
            log.error("access token is invalidate");
            throw new JwtException("자격 증명이 필요한 토큰입니다.");
        }
        log.debug("case1 : access token is validate");

        if (redisService.hasJwtBlackList(accessToken)) {
            log.error("access token is in black list");
            throw new JwtBlackListException("블랙 리스트 토큰입니다.");
        }
        log.debug("case2 : access token in not in blacklist");

        Integer userId = Integer.valueOf(jwtTokenProvider.extractUserId(requestAccessToken));
        log.debug("user id = {}", userId);

        if (redisService.hasRefreshToken(userId)) {
            if (!requestRefreshToken.equals(redisService.getRefreshToken(userId))) {
                log.error("refresh token does not match in Redis.");
                redisService.saveJwtBlackList(requestAccessToken);
                throw new RefreshTokenException("Refresh Token 값이 일치하지 않습니다.");
            }
        } else {
            Auth auth = authQueryJpaRepository.findAuthById(userId);
            if(auth == null) throw new UserNotFoundException("해당 유저를 찾을 수 없습니다.");
            String refreshToken = auth.getRefreshToken();
            log.debug("detectConcurrentUser.requestRefreshToken = {}", requestRefreshToken);
            log.debug("detectConcurrentUser.refreshToken = {}", refreshToken);
            if (!refreshToken.equals(requestRefreshToken)) {
                log.error("refresh token does not match in Database.");
                redisService.saveJwtBlackList(requestAccessToken);
                throw new RefreshTokenException("Refresh Token 값이 일치하지 않습니다.");
            }
        }
        log.debug("2. refresh token is identical.");

    }
}
