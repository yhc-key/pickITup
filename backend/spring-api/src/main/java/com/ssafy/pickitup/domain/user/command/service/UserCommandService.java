package com.ssafy.pickitup.domain.user.command.service;

import com.ssafy.pickitup.domain.auth.command.dto.UserSignupDto;
import com.ssafy.pickitup.domain.auth.entity.Auth;
import com.ssafy.pickitup.domain.badge.command.BadgeCommandService;
import com.ssafy.pickitup.domain.badge.query.BadgeQueryService;
import com.ssafy.pickitup.domain.keyword.entity.Keyword;
import com.ssafy.pickitup.domain.keyword.repository.KeywordQueryJpaRepository;
import com.ssafy.pickitup.domain.recruit.query.RecruitQueryService;
import com.ssafy.pickitup.domain.user.command.repository.UserClickCommandJpaRepository;
import com.ssafy.pickitup.domain.user.command.repository.UserCommandJpaRepository;
import com.ssafy.pickitup.domain.user.command.repository.UserCommandMongoRepository;
import com.ssafy.pickitup.domain.user.command.repository.UserKeywordCommandJpaRepository;
import com.ssafy.pickitup.domain.user.command.repository.UserRecruitCommandJpaRepository;
import com.ssafy.pickitup.domain.user.dto.UserUpdateRequestDto;
import com.ssafy.pickitup.domain.user.entity.Rank;
import com.ssafy.pickitup.domain.user.entity.User;
import com.ssafy.pickitup.domain.user.entity.UserClick;
import com.ssafy.pickitup.domain.user.entity.UserKeyword;
import com.ssafy.pickitup.domain.user.entity.UserMongo;
import com.ssafy.pickitup.domain.user.entity.UserRecruit;
import com.ssafy.pickitup.domain.user.exception.UserNotFoundException;
import com.ssafy.pickitup.domain.user.query.UserQueryJpaRepository;
import com.ssafy.pickitup.domain.user.query.UserQueryService;
import com.ssafy.pickitup.domain.user.query.UserRecruitQueryJpaRepository;
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
    private final UserRecruitQueryJpaRepository userRecruitQueryJpaRepository;
    private final GeoLocationService geoLocationService;
    private final UserQueryJpaRepository userQueryJpaRepository;
    private final BadgeCommandService badgeCommandService;
    private final BadgeQueryService badgeQueryService;
    private final RecruitQueryService recruitQueryService;
    private final UserQueryService userQueryService;
    private final UserRankService userRankService;

    @Transactional
    public UserResponseDto getUserById(int userId) {

        User user = userQueryJpaRepository.findById(userId)
            .orElseThrow(() -> new UserNotFoundException("해당 유저를 찾을 수 없습니다"));
        int scrapCount = userRecruitQueryJpaRepository.countByUserId(userId); // 전체 스크랩 공고 개수
        badgeCommandService.renewBadge(userId); // 뱃지 갱신
        int badgeCount = badgeQueryService.myBadgeCount(userId); // 소유한 뱃지 개수 카운트
        List<Integer> myRecruitIdList = userQueryService.findRecruitIdByUserId(userId);
        int closingCount = recruitQueryService.countClosingRecruitByIdList(myRecruitIdList);
        /*
            뱃지 갱신 -> ok

            내가 가진 뱃지 개수 카운트 -> ok

            스크랩 한 공고 중 마감 임박 채용 공고 개수 카운트 -> ok

            기술 면접 대비 문제 풀이 수 카운트
         */

        log.info("scrapCount : {}", scrapCount);
        log.info("badgeCount : {}", badgeCount);
        log.info("closingCount : {}", closingCount);

        log.info("user = {}", user.toString());
        log.info("user level before = {}", user.getLevel());
        // 유저 레벨 업데이트
        User updatedUser = userRankService.updateLevel(user);
        log.info("user level after = {}", updatedUser.getLevel());

        return UserResponseDto.toDto(updatedUser, scrapCount, badgeCount, closingCount);
    }

    @Transactional
    public UserResponseDto create(Auth auth, UserSignupDto userSignupDto) {
        String nickname = userSignupDto != null ? userSignupDto.getNickname() : auth.getName();
        return createUser(userSignupDto, auth);
    }

    @Transactional
    public UserResponseDto create(Auth auth) {
        return createUser(auth.getName(), auth);
    }


    private UserResponseDto createUser(String nickname, Auth auth) {
        User user = User.builder()
            .nickname(nickname)
            .auth(auth)
            .userRank(Rank.NORMAL)
            .level(1)
            .build();
        userCommandJpaRepository.save(user);
        return UserResponseDto.toDto(user, 0, 0, 0);
    }

    private UserResponseDto createUser(UserSignupDto userSignupDto, Auth auth) {
        User user = User.builder()
            .nickname(userSignupDto.getNickname())
            .address(userSignupDto.getAddress() != null ? userSignupDto.getAddress() : null)
            .auth(auth)
            .userRank(Rank.NORMAL)
            .level(1)
            .build();
        userCommandJpaRepository.save(user);
        return UserResponseDto.toDto(user, 0, 0, 0);
    }

    @Transactional
    public void changeNickname(Integer authId, String nickname) {
        User user = userCommandJpaRepository.findByAuthId(authId);
        user.changeNickname(nickname);
    }

    @Transactional
    public void changeAddress(Integer authId, String address) {
        User user = userCommandJpaRepository.findByAuthId(authId);
        user.changeAddress(address);
    }

    @Transactional
    public void changeUserInfo(Integer authId, UserUpdateRequestDto userUpdateRequestDto) {
        User user = userCommandJpaRepository.findById(authId)
            .orElseThrow(() -> new UserNotFoundException("해당 유저를 찾을 수 없습니다."));
        if (userUpdateRequestDto.getEmail() != null) {
            user.getAuth().changeEmail(userUpdateRequestDto.getEmail());
        }
        if (userUpdateRequestDto.getGithub() != null) {
            user.changeGithub(userUpdateRequestDto.getGithub());
        }
        if (userUpdateRequestDto.getTechBlog() != null) {
            user.changeTechBlog(userUpdateRequestDto.getTechBlog());
        }
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
    public void allUserToMongo() {
        // 현재 mysql에 있는 모든 유저 데이터를 몽고 db로 마이그레이션
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
