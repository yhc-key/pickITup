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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserQueryService {

    private final UserQueryJpaRepository userQueryJpaRepository;
    private final UserRecruitQueryJpaRepository userRecruitJpaRepository;
    private final RecruitQueryService recruitQueryService;
    private final UserKeywordQueryJpaRepository userKeywordQueryJpaRepository;

    @Transactional(readOnly = true)
    public Page<RecruitQueryResponseDto> findMyRecruitById(Integer authId, Pageable pageable) {

        return recruitQueryService.searchByIdList(findRecruitIdByUserId(authId), pageable);
    }

    @Transactional(readOnly = true)
    public List<Integer> findRecruitIdByUserId(Integer userId) {
        List<UserRecruit> userRecruitList = userRecruitJpaRepository.findAllByUserId(userId);
        List<Integer> recruitIdList = new ArrayList<>();
        for (UserRecruit userRecruit : userRecruitList) {
            recruitIdList.add(userRecruit.getId());
        }
        return recruitIdList;
    }

    @Transactional(readOnly = true)
    public KeywordResponseDto findUserKeywords(int authId) {
        User user = userQueryJpaRepository.findById(authId)
            .orElseThrow(() -> new UserNotFoundException("해당 유저를 찾을 수 없습니다"));

        List<UserKeyword> userKeywords = userKeywordQueryJpaRepository.findAllByUserId(authId);
        List<String> keywordsName = userKeywords.stream()
            .map(userKeyword -> userKeyword.getKeyword().getName())
            .toList();

        return new KeywordResponseDto(keywordsName);
    }
}
