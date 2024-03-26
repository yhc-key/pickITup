package com.ssafy.pickitup.domain.badge.query;

import com.ssafy.pickitup.domain.badge.entity.Badge;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BadgeQueryJpaRepository extends JpaRepository<Badge, Integer> {

}
