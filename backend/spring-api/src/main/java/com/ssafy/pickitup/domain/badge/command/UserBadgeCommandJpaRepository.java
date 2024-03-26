package com.ssafy.pickitup.domain.badge.command;

import com.ssafy.pickitup.domain.badge.entity.UserBadge;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserBadgeCommandJpaRepository extends JpaRepository<UserBadge, Integer> {

}
