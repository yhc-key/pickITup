package com.ssafy.pickitup.security.service;

import com.ssafy.pickitup.security.jwt.JwtTokenProvider;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class RedisService {

    private final RedisTemplate<String, String> refreshTokenRedisTemplate;
    private final RedisTemplate<String, String> jwtBlackListRedisTemplate;
    private final JwtTokenProvider jwtTokenProvider;
    private final String RT_PREFIX = "rt:{";
    private final String SUFFIX = "}";
    private final String BL_PREFIX = "bl:{";
    private final Long RT_EXPIRATION_TIME = 60 * 24L; // 1day

    public void saveRefreshToken(Integer authId, String refreshToken) {
        refreshTokenRedisTemplate.opsForValue()
            .set(RT_PREFIX + authId + SUFFIX, refreshToken, RT_EXPIRATION_TIME,
                TimeUnit.MINUTES);
    }

    public String getRefreshToken(Integer authId) {
        return refreshTokenRedisTemplate.opsForValue().get(RT_PREFIX + authId + SUFFIX);
    }

    public void deleteRefreshToken(Integer userId) {
        refreshTokenRedisTemplate.delete(RT_PREFIX + userId + SUFFIX);
    }

    public boolean hasRefreshToken(Integer userId) {
        return Boolean.TRUE.equals(refreshTokenRedisTemplate.hasKey(RT_PREFIX + userId + SUFFIX));
    }

    public void saveJwtBlackList(String accessToken) {
        long expirationTime = jwtTokenProvider.getTokenExpiration(accessToken);
        jwtBlackListRedisTemplate.opsForValue()
            .set(BL_PREFIX + jwtTokenProvider.resolveToken(accessToken) + SUFFIX, "logout",
                expirationTime,
                TimeUnit.MILLISECONDS);
    }

    public boolean hasJwtBlackList(String token) {
        return Boolean.TRUE.equals(jwtBlackListRedisTemplate.hasKey(BL_PREFIX + token + SUFFIX));
    }
}