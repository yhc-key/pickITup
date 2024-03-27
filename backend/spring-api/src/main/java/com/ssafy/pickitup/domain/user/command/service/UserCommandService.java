package com.ssafy.pickitup.domain.user.command.service;

import com.ssafy.pickitup.domain.auth.command.dto.UserSignupDto;
import com.ssafy.pickitup.domain.auth.entity.Auth;
import com.ssafy.pickitup.domain.keyword.entity.Keyword;
import com.ssafy.pickitup.domain.keyword.repository.KeywordQueryJpaRepository;
import com.ssafy.pickitup.domain.user.command.repository.UserClickCommandJpaRepository;
import com.ssafy.pickitup.domain.user.command.repository.UserCommandJpaRepository;
import com.ssafy.pickitup.domain.user.command.repository.UserCommandMongoRepository;
import com.ssafy.pickitup.domain.user.command.repository.UserKeywordCommandJpaRepository;
import com.ssafy.pickitup.domain.user.command.repository.UserRecruitCommandJpaRepository;
import com.ssafy.pickitup.domain.user.entity.User;
import com.ssafy.pickitup.domain.user.entity.UserClick;
import com.ssafy.pickitup.domain.user.entity.UserKeyword;
import com.ssafy.pickitup.domain.user.entity.UserMongo;
import com.ssafy.pickitup.domain.user.entity.UserRecruit;
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
    private final UserRecruitCommandJpaRepository userRecruitCommandJpaRepository;
    private final UserClickCommandJpaRepository userClickCommandJpaRepository;
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
        return UserResponseDto.toDto(user, 0);
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
        /**
         * scala.call(1, "create")
         * scala.call(1, "update")
         */

    }

    @Transactional
    public void updateUserKeyword(Integer authId, KeywordRequestDto keywordRequestDto) {
        userKeywordCommandJpaRepository.deleteAllByUserId(authId);
        addKeywords(authId, keywordRequestDto);
    }

    @Transactional
    public void saveUserRecruit(Integer authId, Integer recruitId) {
        User user = userCommandJpaRepository.findByAuthId(authId);
        UserRecruit userRecruit = new UserRecruit(user, recruitId);
        userRecruitCommandJpaRepository.save(userRecruit);
    }

    @Transactional
    public void saveUserClick(Integer authId, Integer recruitId) {
        User user = userCommandJpaRepository.findByAuthId(authId);
        UserClick userClick = userClickCommandJpaRepository.findByUserIdAndRecruitId(
            user.getId(), recruitId);
        if (userClick == null) {
            UserClick newUserClick = new UserClick(user, recruitId);
            userClickCommandJpaRepository.save(newUserClick);
        } else {
            userClick.increaseClickCount();
        }

    }


    @Transactional
    public void deleteUserRecruit(Integer authId, Integer recruitId) {
        userRecruitCommandJpaRepository.deleteAllByUserIdAndRecruitId(authId, recruitId);
    }

    @Transactional
    public void increaseUserAttendCount(Integer authId) {
        User user = userCommandJpaRepository.findByAuthId(authId);
        //출석 횟수 증가
        user.increaseAttendCount();
    }

}
