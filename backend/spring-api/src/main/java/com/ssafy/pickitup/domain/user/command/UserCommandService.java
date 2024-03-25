package com.ssafy.pickitup.domain.user.command;

import com.ssafy.pickitup.domain.auth.command.dto.UserSignupDto;
import com.ssafy.pickitup.domain.auth.entity.Auth;
import com.ssafy.pickitup.domain.user.entity.User;
import com.ssafy.pickitup.domain.user.entity.UserKeyword;
import com.ssafy.pickitup.domain.user.entity.UserMongo;
import com.ssafy.pickitup.domain.user.keyword.Keyword;
import com.ssafy.pickitup.domain.user.keyword.KeywordQueryJpaRepository;
import com.ssafy.pickitup.domain.user.query.dto.KeywordRequestDto;
import com.ssafy.pickitup.domain.user.query.dto.UserResponseDto;
import com.ssafy.pickitup.global.entity.GeoLocation;
import com.ssafy.pickitup.global.service.GeoLocationService;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserCommandService {

    private final UserCommandJpaRepository userCommandJpaRepository;
    private final UserCommandMongoRepository userCommandMongoRepository;
    private final UserKeywordCommandJpaRepository userKeywordCommandJpaRepository;
    private final KeywordQueryJpaRepository keywordQueryJpaRepository;
    private final GeoLocationService geoLocationService;

    @Transactional
    public UserResponseDto create(Auth auth, UserSignupDto userSignupDto) {
        String nickname = userSignupDto != null ? userSignupDto.getNickname() : auth.getName();
        return createUser(nickname, auth);
    }

    @Transactional
    public UserResponseDto create(Auth auth) {
        return createUser(auth.getName(), auth);
    }

    private UserResponseDto createUser(String nickname, Auth auth) {
        User user = User.builder()
            .nickname(nickname)
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
        User user = userCommandJpaRepository.findByAuthId(authId);
        List<Integer> keywordIdList = keywordRequestDto.getKeywords();

        List<Keyword> keywords = keywordQueryJpaRepository.findAllById(keywordIdList);
        List<UserKeyword> userKeywords = keywords.stream()
            .map(keyword -> new UserKeyword(user, keyword))
            .toList();

        user.setUserKeywords(userKeywords);

        // 로그 출력
        List<String> keywordsNameList = userKeywords.stream()
            .map(userKeyword -> userKeyword.getKeyword().getName())
            .toList();
        log.info("keywordsNameList = {}", keywordsNameList);

        // UserMongo 업데이트
        GeoLocation geoLocation = geoLocationService.getGeoLocation(user.getAddress());
        UserMongo userMongo = userCommandMongoRepository.findById(authId)
            .orElseGet(() -> new UserMongo(authId, new ArrayList<>(), geoLocation.getLatitude(),
                geoLocation.getLongitude()));
        userMongo.setKeywords(keywordsNameList);
        userCommandMongoRepository.save(userMongo);
    }

    @Transactional
    public void updateUserKeyword(Integer authId, KeywordRequestDto keywordRequestDto) {

        //기존의 키워드 삭제해주고
        userKeywordCommandJpaRepository.deleteAllByUserId(authId);
        addKeywords(authId, keywordRequestDto);
    }
}
