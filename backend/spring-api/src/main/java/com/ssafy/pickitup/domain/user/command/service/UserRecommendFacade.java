package com.ssafy.pickitup.domain.user.command.service;

import com.ssafy.pickitup.domain.user.dto.UserRecommendDto;
import com.ssafy.pickitup.domain.user.entity.Rank;
import com.ssafy.pickitup.domain.user.entity.User;
import com.ssafy.pickitup.domain.user.exception.UserNotFoundException;
import com.ssafy.pickitup.domain.user.query.UserQueryJpaRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserRecommendFacade {

    private final UserRecommendService userRecommendService;
    private final UserQueryJpaRepository userQueryJpaRepository;

    public List<UserRecommendDto> getUserRecommendList(Integer userId) {

        //user가 기술스택과  address를 입력했을 때만 추천 서비스를 이용할 수 있으므로 validation check
        User user = userQueryJpaRepository.findById(userId).orElseThrow(UserNotFoundException::new);

        log.debug("user = {}", user.toString());
        if (user.getAddress() == null || user.getAddress().length() == 0) {
            log.debug("user address 가 없습니다.");
            return null;
        }

        if (user.getUserKeywords() == null || user.getUserKeywords().size() == 0) {
            log.debug("user keywords 가 없습니다. = {}");
            return null;
        }

        return userRecommendService.getUserRecommendRecruitList(userId,
            user.getUserRank().equals(Rank.SUPER));
    }

}
