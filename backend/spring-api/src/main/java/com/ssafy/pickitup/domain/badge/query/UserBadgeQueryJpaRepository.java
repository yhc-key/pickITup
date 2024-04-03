package com.ssafy.pickitup.domain.badge.query;

import com.ssafy.pickitup.domain.badge.entity.UserBadge;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserBadgeQueryJpaRepository extends JpaRepository<UserBadge, Integer> {

    List<UserBadge> findByUserId(Integer userId);

    @Query(value = "SELECT COUNT(user_badge.id) FROM user_badge WHERE user_badge.user_id= :userId AND user_badge.is_achieved = true", nativeQuery = true)
    int countAchievedBadgesByUserId(Integer userId);
}
