package com.ssafy.pickitup.domain.user.command;

import com.ssafy.pickitup.domain.auth.command.dto.UserSignupDto;
import com.ssafy.pickitup.domain.auth.entity.Auth;
import com.ssafy.pickitup.domain.user.entity.User;
import com.ssafy.pickitup.domain.user.entity.UserKeyword;
import com.ssafy.pickitup.domain.user.entity.UserRecruit;
import com.ssafy.pickitup.domain.user.keyword.Keyword;
import com.ssafy.pickitup.domain.user.keyword.KeywordQueryJpaRepository;
import com.ssafy.pickitup.domain.user.query.dto.KeywordRequestDto;
import com.ssafy.pickitup.domain.user.query.dto.UserResponseDto;
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
    private final UserKeywordCommandJpaRepository userKeywordCommandJpaRepository;
    private final KeywordQueryJpaRepository keywordQueryJpaRepository;
    private final UserRecruitCommandJpaRepository userRecruitCommandJpaRepository;

    @Transactional
    public UserResponseDto create(Auth auth, UserSignupDto userSignupDto) {
        User user = User.builder()
            .nickname(userSignupDto.getNickname())
            .auth(auth)
            .build();
        System.out.println("user.toString() = " + user.toString());
        userCommandJpaRepository.save(user);
        return UserResponseDto.toDto(user, 0);
    }

    @Transactional
    public UserResponseDto create(Auth auth) {
        User user = User.builder()
            .nickname(auth.getName())
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
        List<Integer> keywordIdList = keywordRequestDto.getKeywords();
        User user = userCommandJpaRepository.findByAuthId(authId);
        List<Keyword> keywords = new ArrayList<>();
        for (Integer id : keywordIdList) {
            keywords.add(keywordQueryJpaRepository.findKeywordById(id));
        }
        user.setUserKeywords(
            keywords.stream()
                .map(keyword -> new UserKeyword(user, keyword))
                .toList()
        );

//        userKeywordCommandJpaRepository.saveUserAndKeywords(authId, keywords);

        List<String> keywordsNameList = user.getUserKeywords()
            .stream()
            .map(userKeyword -> userKeyword.getKeyword().getName())
            .toList();
        log.info("keywordsNameList = {}", keywordsNameList);

//        User Mongo에 키워드 추가

    }

    @Transactional
    public void updateUserKeyword(Integer authId, KeywordRequestDto keywordRequestDto) {
        User user = userCommandJpaRepository.findByAuthId(authId);

        List<UserKeyword> userKeywords = new ArrayList<>();
        List<Integer> keywordIdList = keywordRequestDto.getKeywords();
        List<Keyword> keywords = new ArrayList<>();
        for (Integer id : keywordIdList) {
            keywords.add(keywordQueryJpaRepository.findKeywordById(id));
        }

        //기존의 키워드 삭제해주고
        userKeywordCommandJpaRepository.deleteAllByUserId(user.getId());

        user.setUserKeywords(
            keywords.stream()
                .map(keyword -> new UserKeyword(user, keyword))
                .toList()
        );

        List<String> keywordsNameList = user.getUserKeywords()
            .stream()
            .map(userKeyword -> userKeyword.getKeyword().getName())
            .toList();
        log.info("keywordsNameList = {}", keywordsNameList);

//        User Mongo에 키워드 추가

    }

    @Transactional
    public void saveUserRecruit(Integer authId, Integer recruitId) {
        User user = userCommandJpaRepository.findByAuthId(authId);
        UserRecruit userRecruit = new UserRecruit(user, recruitId);
        userRecruitCommandJpaRepository.save(userRecruit);
    }

    @Transactional
    public void deleteUserRecruit(Integer authId, Integer recruitId) {
        userRecruitCommandJpaRepository.deleteAllByUserIdAndRecruitId(authId, recruitId);
    }
}
