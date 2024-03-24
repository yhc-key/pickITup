package com.ssafy.pickitup.domain.user.command;

import com.ssafy.pickitup.domain.user.entity.Keyword;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KeywordCommandJpaRepository extends JpaRepository<Keyword, Integer> {
}
