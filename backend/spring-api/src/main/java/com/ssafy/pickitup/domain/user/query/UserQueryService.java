package com.ssafy.pickitup.domain.user.query;

import com.ssafy.pickitup.domain.recruit.query.RecruitQueryService;
import com.ssafy.pickitup.domain.recruit.query.dto.RecruitQueryResponseDto;
import com.ssafy.pickitup.domain.user.entity.User;
import com.ssafy.pickitup.domain.user.exception.UserNotFoundException;
import com.ssafy.pickitup.domain.user.query.dto.UserResponseDto;
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
    private final UserRecruitJpaRepository userRecruitJpaRepository;
    private final RecruitQueryService recruitQueryService;

    public UserResponseDto getUserById(int id) {
        User user = userQueryJpaRepository.findById(id);
        if (user == null) {
            throw new UserNotFoundException("해당 유저를 찾을 수 없습니다");
        }

        return UserResponseDto.toDto(user);
    }

    @Transactional(readOnly = true)
    public Page<RecruitQueryResponseDto> findMyRecruitById(int authId, Pageable pageable) {
        List<Integer> myRecruitIdList = userRecruitJpaRepository.findAllByUserId(authId);

        return recruitQueryService.searchByIdList(
            myRecruitIdList, pageable);
    }
}
