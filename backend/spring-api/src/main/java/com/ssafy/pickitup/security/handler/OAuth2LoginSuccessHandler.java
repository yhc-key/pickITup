package com.ssafy.pickitup.security.handler;

import com.ssafy.pickitup.domain.auth.command.AuthCommandService;
import com.ssafy.pickitup.domain.auth.entity.Auth;
import com.ssafy.pickitup.domain.auth.query.AuthQueryService;
import com.ssafy.pickitup.domain.auth.query.dto.AuthDto;
import com.ssafy.pickitup.security.entity.RefreshToken;
import com.ssafy.pickitup.security.jwt.JwtProperties;
import com.ssafy.pickitup.security.jwt.JwtTokenDto;
import com.ssafy.pickitup.security.jwt.JwtTokenProvider;
import com.ssafy.pickitup.security.service.RedisService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtTokenProvider jwtTokenProvider;
    private final AuthQueryService authQueryService;
    private final AuthCommandService authCommandService;
    private final RedisService redisService;
    private final String CALLBACK_URL = "http://localhost:3000/auth/callback";

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
        Authentication authentication) throws IOException, ServletException {

        JwtTokenDto tokenSet = jwtTokenProvider.generateToken(authentication);
        // DB에 Refreshtoken 저장
//        user.setRefreshToken(tokenSet.getRefreshToken());
//        userCommandService.saveUser(user);
        // DB에 Refreshtoken 저장
        AuthDto authDto = authQueryService.getUserByUsername(authentication.getName());
        authDto.setRefreshToken(tokenSet.getRefreshToken());
        Auth updatedAuth = Auth.toDto(authDto);
        // Redis에 Refreshtoken 저장
        RefreshToken refreshToken = RefreshToken.builder()
            .userId(authentication.getName())
            .refreshToken(tokenSet.getRefreshToken())
            .build();
        redisService.saveRefreshToken(refreshToken.getUserId(), refreshToken.getRefreshToken());
        // token 쿼리스트링
        String targetUrl = UriComponentsBuilder.fromUriString(CALLBACK_URL)
            .queryParam(JwtProperties.TOKEN_TYPE, JwtProperties.TOKEN_PREFIX.substring(0, 6))
            .queryParam(JwtProperties.ACCESS_TOKEN, tokenSet.getAccessToken())
            .queryParam(JwtProperties.EXPRIES_IN, JwtProperties.ACCESS_TOKEN_EXPIRATION_TIME)
            .queryParam(JwtProperties.REFRESH_TOKEN, tokenSet.getRefreshToken())
            .build().toUriString();
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }

}
