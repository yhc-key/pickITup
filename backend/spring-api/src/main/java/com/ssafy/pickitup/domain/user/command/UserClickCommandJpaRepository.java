package com.ssafy.pickitup.domain.user.command;

import com.ssafy.pickitup.domain.user.entity.UserClick;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserClickCommandJpaRepository extends JpaRepository<UserClick, Integer> {

    UserClick findByUserIdAndRecruitId(Integer userId, Integer recruitIt);

    List<UserClick> findAllByUserId(Integer userId);
}
