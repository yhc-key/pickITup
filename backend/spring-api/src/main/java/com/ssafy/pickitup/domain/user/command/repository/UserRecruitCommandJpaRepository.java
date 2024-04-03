package com.ssafy.pickitup.domain.user.command.repository;

import com.ssafy.pickitup.domain.user.entity.UserRecruit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRecruitCommandJpaRepository extends JpaRepository<UserRecruit, Integer> {

    void deleteAllByUserIdAndRecruitId(int userId, int recruitId);
}
