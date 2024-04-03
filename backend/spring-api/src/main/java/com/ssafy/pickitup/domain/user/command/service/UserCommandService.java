package com.ssafy.pickitup.domain.user.command.service;

import com.ssafy.pickitup.domain.auth.command.dto.UserSignupDto;
import com.ssafy.pickitup.domain.auth.entity.Auth;
import com.ssafy.pickitup.domain.badge.command.BadgeCommandService;
import com.ssafy.pickitup.domain.badge.query.BadgeQueryService;
import com.ssafy.pickitup.domain.keyword.entity.Keyword;
import com.ssafy.pickitup.domain.keyword.repository.KeywordQueryJpaRepository;
import com.ssafy.pickitup.domain.recruit.query.RecruitQueryService;
import com.ssafy.pickitup.domain.user.command.repository.ScrapCommandMongoRepository;
import com.ssafy.pickitup.domain.user.command.repository.UserClickCommandJpaRepository;
import com.ssafy.pickitup.domain.user.command.repository.UserCommandJpaRepository;
import com.ssafy.pickitup.domain.user.command.repository.UserCommandMongoRepository;
import com.ssafy.pickitup.domain.user.command.repository.UserKeywordCommandJpaRepository;
import com.ssafy.pickitup.domain.user.command.repository.UserRecruitCommandJpaRepository;
import com.ssafy.pickitup.domain.user.dto.UserUpdateRequestDto;
import com.ssafy.pickitup.domain.user.entity.ClickMongo;
import com.ssafy.pickitup.domain.user.entity.Rank;
import com.ssafy.pickitup.domain.user.entity.ScrapMongo;
import com.ssafy.pickitup.domain.user.entity.User;
import com.ssafy.pickitup.domain.user.entity.UserClick;
import com.ssafy.pickitup.domain.user.entity.UserKeyword;
import com.ssafy.pickitup.domain.user.entity.UserLevel;
import com.ssafy.pickitup.domain.user.entity.UserMongo;
import com.ssafy.pickitup.domain.user.entity.UserRecruit;
import com.ssafy.pickitup.domain.user.exception.DuplicateScrapException;
import com.ssafy.pickitup.domain.user.exception.ScrapNotFoundException;
import com.ssafy.pickitup.domain.user.exception.UserNotFoundException;
import com.ssafy.pickitup.domain.user.query.UserQueryJpaRepository;
import com.ssafy.pickitup.domain.user.query.UserQueryService;
import com.ssafy.pickitup.domain.user.query.UserRecruitQueryJpaRepository;
import com.ssafy.pickitup.domain.user.query.dto.KeywordRequestDto;
import com.ssafy.pickitup.domain.user.query.dto.UserResponseDto;
import com.ssafy.pickitup.domain.user.query.service.UserInterviewQueryService;
import com.ssafy.pickitup.global.entity.GeoLocation;
import com.ssafy.pickitup.global.service.GeoLocationService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserCommandService {

    private final MongoTemplate mongoTemplate;

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
    private final ScrapCommandMongoRepository scrapCommandMongoRepository;
    private final UserRecommendService userRecommendService;
    private final UserInterviewQueryService userInterviewQueryService;

    @Transactional
    public UserResponseDto getUserById(int userId) {

        User user = userQueryJpaRepository.findById(userId)
            .orElseThrow(UserNotFoundException::new);
        int scrapCount = userRecruitQueryJpaRepository.countByUserId(userId); // 전체 스크랩 공고 개수
        badgeCommandService.renewBadge(userId); // 뱃지 갱신
        int badgeCount = badgeQueryService.myBadgeCount(userId); // 소유한 뱃지 개수 카운트
        List<Integer> myRecruitIdList = userQueryService.findRecruitIdByUserId(userId);
        int closingCount = recruitQueryService.countClosingRecruitByIdList(myRecruitIdList);
        int solvedInterviewAnswerCount = userInterviewQueryService.countSolvedInterviewsByUserId(
            userId);

        log.debug("scrapCount : {}", scrapCount);
        log.debug("badgeCount : {}", badgeCount);
        log.debug("closingCount : {}", closingCount);

        log.debug("user = {}", user.toString());
        log.debug("user level before = {}", user.getLevel());
        // 유저 레벨 업데이트
        User updatedUser = userRankService.updateLevel(user);
        log.debug("user level after = {}", updatedUser.getLevel());

        //유저 경험치 정보
        UserLevel expInfo = userRankService.getExpInfo(updatedUser.getLevel());
        log.debug("expInfo = {}", expInfo);

        //유저 랭크 업데이트
        if (user.getUserRank() == Rank.NORMAL) {
            if (user.checkMyRank()) {
                user.upgradeToSuper();
                UserMongo userMongo = userCommandMongoRepository.findById(userId)
                    .orElseThrow(UserNotFoundException::new);
                userMongo.setRank(Rank.SUPER.name());
            }
        }

        return UserResponseDto.toDto(updatedUser, expInfo, scrapCount, badgeCount, closingCount,
            solvedInterviewAnswerCount);
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
        badgeCommandService.initBadge(user);
        userCommandJpaRepository.save(user);
        auth.setUser(user);
        return UserResponseDto.toDto(user, 0, 0, 0, 0);
    }


    private UserResponseDto createUser(UserSignupDto userSignupDto, Auth auth) {
        User user = User.builder()
            .nickname(userSignupDto.getNickname())
            .address(userSignupDto.getAddress() != null ? userSignupDto.getAddress() : null)
            .auth(auth)
            .userRank(Rank.NORMAL)
            .level(1)
            .build();
        badgeCommandService.initBadge(user);
        userCommandJpaRepository.save(user);
        auth.setUser(user);
        return UserResponseDto.toDto(user, 0, 0, 0, 0);
    }

    @Transactional
    public void changeNickname(Integer userId, String nickname) {
        User user = userCommandJpaRepository.findById(userId)
            .orElseThrow(UserNotFoundException::new);
        user.changeNickname(nickname);
    }


    @Transactional
    public void changeProfile(Integer userId, Integer profile) {
        User user = userCommandJpaRepository.findById(userId)
            .orElseThrow(UserNotFoundException::new);
        user.changeProfile(profile);
    }

    @CacheEvict(cacheNames = "recommend", key = "#authId")
    @Transactional
    public void changeAddress(Integer authId, String address) {
        User user = userCommandJpaRepository.findById(authId)
            .orElseThrow(UserNotFoundException::new);
        user.changeAddress(address);
        updateUserMongo(user);
        //스칼라 서버
        callScalaByAddressChange();
    }

    @Transactional
    public void changeUserInfo(Integer authId, UserUpdateRequestDto userUpdateRequestDto) {
        User user = userCommandJpaRepository.findById(authId)
            .orElseThrow(UserNotFoundException::new);
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
    public void addKeywords(Integer userId, KeywordRequestDto keywordRequestDto) {
        User user = userCommandJpaRepository.findById(userId)
            .orElseThrow(UserNotFoundException::new);
        List<Integer> keywordIdList = keywordRequestDto.getKeywords();

        List<Keyword> keywords = keywordQueryJpaRepository.findAllById(keywordIdList);
        List<UserKeyword> userKeywords = keywords.stream()
            .map(keyword -> new UserKeyword(user, keyword))
            .toList();

        user.setUserKeywords(userKeywords);

        updateUserMongo(user);

        // 스칼라 서버에 유저 키워드 변경 사실 알리기
        callScalaByKeywordChange();
    }

    @Transactional
    public void updateUserMongo(User user) {
        // UserMongo 업데이트
        Integer userId = user.getId();
        List<UserKeyword> userKeywords = user.getUserKeywords();
        List<String> keywordsNameList = userKeywords.stream()
            .map(userKeyword -> userKeyword.getKeyword().getName())
            .toList();
        log.debug("keywordsNameList = {}", keywordsNameList);

        GeoLocation geoLocation = geoLocationService.getGeoLocation(user.getAddress());

        UserMongo userMongo = userCommandMongoRepository.findById(userId)
            .orElseGet(() ->
                UserMongo.builder()
                    .id(userId)
                    .rank(Rank.NORMAL.name())
                    .build());
        userMongo.setKeywords(keywordsNameList);
        userMongo.setLatitude(geoLocation.getLatitude());
        userMongo.setLongitude(geoLocation.getLongitude());

        userCommandMongoRepository.save(userMongo);
    }

    @CacheEvict(cacheNames = "recommend", key = "#authId")
    @Transactional
    public void updateUserKeyword(Integer authId, KeywordRequestDto keywordRequestDto) {
        userKeywordCommandJpaRepository.deleteAllByUserId(authId);
        addKeywords(authId, keywordRequestDto);
    }

    @Transactional
    public void saveUserRecruit(Integer userId, Integer recruitId) {
        User user = userCommandJpaRepository.findById(userId)
            .orElseThrow(UserNotFoundException::new);
        UserRecruit userRecruit = new UserRecruit(user, recruitId);
        if (scrapCommandMongoRepository.existsByUserIdAndRecruitId(userId, recruitId)) {
            throw new DuplicateScrapException();
        }
        user.increaseRecruitScrapCount();
        scrapCommandMongoRepository.save(ScrapMongo.createScrap(userId, recruitId));
        userRecruitCommandJpaRepository.save(userRecruit);
    }

    @Transactional
    public void increaseUserClick(Integer userId, Integer recruitId) {
        User user = userCommandJpaRepository.findById(userId)
            .orElseThrow(UserNotFoundException::new);
        UserClick userClick = userClickCommandJpaRepository
            .findByUserIdAndRecruitId(userId, recruitId)
            .orElse(new UserClick(user, recruitId));
        userClick.increaseClickCount();
        user.increaseRecruitViewCount();
        userClickCommandJpaRepository.save(userClick);

        Query query = new Query(Criteria.where("userId").is(userId).and("recruitId").is(recruitId));
        ClickMongo clickMongo = mongoTemplate.findOne(query, ClickMongo.class);

        if (clickMongo == null) {
            clickMongo = ClickMongo.createClick(userId, recruitId);
        } else {
            clickMongo.increaseClickCount();
        }

        mongoTemplate.save(clickMongo);
    }

    @Transactional
    public void deleteUserRecruit(Integer authId, Integer recruitId) {
        ScrapMongo scrapMongo = scrapCommandMongoRepository
            .findByUserIdAndRecruitId(authId, recruitId)
            .orElseThrow(ScrapNotFoundException::new);
        scrapCommandMongoRepository.delete(scrapMongo);
        userRecruitCommandJpaRepository.deleteAllByUserIdAndRecruitId(authId, recruitId);
    }

    @Transactional
    public void increaseUserAttendCount(Integer authId) {
        User user = userCommandJpaRepository.findById(authId)
            .orElseThrow(UserNotFoundException::new);
        //출석 횟수 증가
        user.increaseAttendCount();
    }

    @Transactional
    public void scala() {
        userRecommendService.sendRequestToScalaServer();
    }

    private void callScalaByKeywordChange() {
        userRecommendService.sendSignalToScalaServerByKeywordChange();
    }

    @Transactional
    public void callScalaByAddressChange() {
        userRecommendService.sendSignalToScalaServerByAddressChange();
    }
}
