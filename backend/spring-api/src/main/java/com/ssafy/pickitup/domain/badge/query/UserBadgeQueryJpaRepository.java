package com.ssafy.pickitup.domain.badge.query;

import com.ssafy.pickitup.domain.badge.entity.UserBadge;
import com.ssafy.pickitup.domain.user.entity.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserBadgeQueryJpaRepository extends JpaRepository<UserBadge, Integer> {

    List<UserBadge> findByUser(User user);
}
