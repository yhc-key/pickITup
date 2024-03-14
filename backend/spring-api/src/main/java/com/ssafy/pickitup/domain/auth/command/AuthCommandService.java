package com.ssafy.pickitup.domain.auth.command;

import com.ssafy.pickitup.domain.auth.command.dto.LoginRequestDto;
import com.ssafy.pickitup.domain.auth.command.dto.LogoutDto;
import com.ssafy.pickitup.domain.auth.command.dto.UserSignupDto;
import com.ssafy.pickitup.domain.auth.entity.Auth;
import com.ssafy.pickitup.domain.auth.entity.Role;
import com.ssafy.pickitup.domain.auth.query.dto.AuthDto;
import com.ssafy.pickitup.domain.user.command.UserCommandService;
import com.ssafy.pickitup.domain.user.query.dto.UserResponseDto;
import com.ssafy.pickitup.security.entity.RefreshToken;
import com.ssafy.pickitup.security.exception.AuthNotFoundException;
import com.ssafy.pickitup.security.exception.PasswordException;
import com.ssafy.pickitup.security.jwt.JwtTokenDto;
import com.ssafy.pickitup.security.jwt.JwtTokenProvider;
import com.ssafy.pickitup.security.service.RedisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthCommandService {


    private final AuthCommandJpaRepository authCommandJpaRepository;
    private final UserCommandService userCommandService;
    private final BCryptPasswordEncoder passwordEncoder;
    private final RedisService redisService;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    @Transactional
    public UserResponseDto signup(UserSignupDto userSignupDto) {
        Auth auth = Auth.builder()
            .username(userSignupDto.getUsername())
            .name(userSignupDto.getName())
            .password(passwordEncoder.encode(userSignupDto.getPassword()))
            .email(userSignupDto.getEmail())
            .role(Role.USER)
            .build();
        authCommandJpaRepository.save(auth);
        return userCommandService.create(auth, userSignupDto);
    }


    @Transactional
    public JwtTokenDto login(LoginRequestDto loginRequestDto){

        log.info("login request username= {}", loginRequestDto.getUsername());
        log.info("login request password= {}", loginRequestDto.getPassword());

//        AuthDto authDto = authCommandJpaRepository.findAuthByUsername(loginRequestDto.getUsername());
        Auth auth = authCommandJpaRepository.findAuthByUsername(loginRequestDto.getUsername());
        AuthDto authDto = AuthDto.getAuth(auth);
        if(auth == null){
            throw new AuthNotFoundException("존재하지 않는 아이디입니다.");
        }

        log.info("auth= {}", authDto.toString());
        if(!passwordEncoder.matches(loginRequestDto.getPassword(), authDto.getPassword())){
            throw new PasswordException("비밀번호가 일치하지 않습니다.");
        }


        // Login ID/PW를 기반으로 Authentication Token 생성
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken
            = new UsernamePasswordAuthenticationToken(loginRequestDto.getUsername(), loginRequestDto.getPassword());
        System.out.println(
            "usernamePasswordAuthenticationToken.toString() = " + usernamePasswordAuthenticationToken.toString());
        // 실제로 검증이 이루어지는 부분
        Authentication authentication =
            authenticationManagerBuilder.getObject()
                .authenticate(usernamePasswordAuthenticationToken);
        // 인증 정보를 기반으로 JWT 토큰 생성
        SecurityContextHolder.getContext().setAuthentication(authentication);

        JwtTokenDto tokenSet = jwtTokenProvider.generateToken(authentication, authDto);
        // DB에 Refreshtoken 저장
        authDto.setRefreshToken(tokenSet.getRefreshToken());
        Auth updatedAuth = Auth.toDto(authDto);
//        updatedAuth.setRefreshToken(tokenSet.getRefreshToken());
        log.info("updatedAuth = {}", updatedAuth.toString());
        authCommandJpaRepository.save(updatedAuth);

        // RefreshToken Redis에 저장
        RefreshToken refreshToken = RefreshToken.builder()
            .userId(authentication.getName())
            .refreshToken(tokenSet.getRefreshToken())
            .build();

        redisService.saveRefreshToken(refreshToken.getUserId(), refreshToken.getRefreshToken());

        log.debug("RefreshToken in Redis = {}", refreshToken.getRefreshToken());
        return tokenSet;
    }

    public LogoutDto logout(String accessToken) {
        String token = jwtTokenProvider.resolveToken(accessToken);
        log.debug("token = {}", token);
        int userId = Integer.parseInt(jwtTokenProvider.extractUserId(accessToken));
        log.debug("principal = {}", userId);
        redisService.saveJwtBlackList(accessToken);
        redisService.deleteRefreshToken(userId);
        Auth auth = authCommandJpaRepository.findAuthById(userId);
//        AuthDto authDto = AuthDto.getAuth(auth);
//        authDto.setRefreshToken(null);
        auth.deleteRefreshToken();
//        Auth auth = Auth.toDto(authDto);
        authCommandJpaRepository.save(auth);
//        authCommandJpaRepository.save(Auth.getAuth(authDto));
        return new LogoutDto(auth.getUsername());
    }

}
