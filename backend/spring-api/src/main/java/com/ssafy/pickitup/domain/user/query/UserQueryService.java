package com.ssafy.pickitup.domain.user.query;

import com.ssafy.pickitup.domain.recruit.query.RecruitQueryService;
import com.ssafy.pickitup.domain.recruit.query.dto.RecruitQueryResponseDto;
import com.ssafy.pickitup.domain.user.entity.User;
import com.ssafy.pickitup.domain.user.entity.UserKeyword;
import com.ssafy.pickitup.domain.user.entity.UserRecruit;
import com.ssafy.pickitup.domain.user.exception.UserNotFoundException;
import com.ssafy.pickitup.domain.user.query.dto.KeywordResponseDto;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserQueryService {

    private final UserQueryJpaRepository userQueryJpaRepository;
    private final UserRecruitQueryJpaRepository userRecruitJpaRepository;
    private final RecruitQueryService recruitQueryService;
    private final UserKeywordQueryJpaRepository userKeywordQueryJpaRepository;

    @Transactional(readOnly = true)
    public List<RecruitQueryResponseDto> findMyRecruitById(Integer authId) {

        return recruitQueryService.searchByIdList(findRecruitIdByUserId(authId));
    }

    @Transactional(readOnly = true)
    public List<Integer> findRecruitIdByUserId(Integer userId) {
        List<UserRecruit> userRecruitList = userRecruitJpaRepository.findAllByUserId(userId);
        List<Integer> recruitIdList = new ArrayList<>();
        for (UserRecruit userRecruit : userRecruitList) {
            recruitIdList.add(userRecruit.getRecruitId());
        }
        return recruitIdList;
    }

    @Transactional(readOnly = true)
    public KeywordResponseDto findUserKeywords(int authId) {
        User user = userQueryJpaRepository.findById(authId)
            .orElseThrow(UserNotFoundException::new);

        List<UserKeyword> userKeywords = userKeywordQueryJpaRepository.findAllByUserId(authId);
        List<String> keywordsName = userKeywords.stream()
            .map(userKeyword -> userKeyword.getKeyword().getName())
            .toList();

        return new KeywordResponseDto(keywordsName);
    }
}
