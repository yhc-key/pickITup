package com.ssafy.pickitup.security.service;

import com.ssafy.pickitup.domain.auth.command.AuthCommandJpaRepository;
import com.ssafy.pickitup.domain.auth.entity.Auth;
import com.ssafy.pickitup.domain.auth.entity.Role;
import com.ssafy.pickitup.domain.auth.query.dto.AuthDto;
import com.ssafy.pickitup.domain.user.command.service.UserCommandService;
import com.ssafy.pickitup.security.CustomUserDetails;
import com.ssafy.pickitup.security.oauth2.GoogleOAuth2UserInfo;
import com.ssafy.pickitup.security.oauth2.KakaoOAuth2UserInfo;
import com.ssafy.pickitup.security.oauth2.NaverOAuth2UserInfo;
import com.ssafy.pickitup.security.oauth2.Oauth2UserInfo;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private static final String NAVER = "naver";
    private static final String KAKAO = "kakao";
    private static final String GET_NAVER_ATTRIBUTE = "response";
    private final AuthCommandJpaRepository authCommandJpaRepository;
    private final UserCommandService userCommandService;
    private final String GOOGLE = "google";
    private Auth auth;

    @Override
    @Transactional
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        log.info("userRequest = {}", userRequest.toString());
        OAuth2User oAuth2User = super.loadUser(userRequest);
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        Oauth2UserInfo oauth2UserInfo = ofOAuth2UserInfo(registrationId, oAuth2User);
        Optional<Auth> optionalUser = authCommandJpaRepository.findByProviderAndProviderId(
            oauth2UserInfo.getProvider(), oauth2UserInfo.getProviderId());
        if (optionalUser.isPresent()) {
            AuthDto authDto = AuthDto.getAuth(optionalUser.get());
            authDto.setEmail(oauth2UserInfo.getEmail());
            auth = Auth.toDto(authDto);
        } else {
            auth = Auth.builder()
                .username(oauth2UserInfo.getProviderId())
                .email(oauth2UserInfo.getEmail())
                .name(oauth2UserInfo.getName())
                .role(Role.USER)
                .provider(oauth2UserInfo.getProvider())
                .providerId(oauth2UserInfo.getProviderId())
                .build();
        }

//        authCommandJpaRepository.save(auth);
        if (optionalUser.isEmpty()) {
            userCommandService.create(auth);
        }

        return new CustomUserDetails(auth, oAuth2User.getAttributes());
    }

    private Oauth2UserInfo ofOAuth2UserInfo(String registrationId, OAuth2User oAuth2User) {
        if (registrationId.equals(GOOGLE)) {
            return new GoogleOAuth2UserInfo(oAuth2User.getAttributes());
        } else if (registrationId.equals(NAVER)) {
            return new NaverOAuth2UserInfo(
                (Map) oAuth2User.getAttributes().get(GET_NAVER_ATTRIBUTE));
        } else if (registrationId.equals(KAKAO)) {
            return new KakaoOAuth2UserInfo(oAuth2User.getAttributes());
        }
        return null;
    }
}