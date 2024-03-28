package com.ssafy.pickitup.domain.user.command.repository;

import com.ssafy.pickitup.domain.keyword.entity.Keyword;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KeywordCommandJpaRepository extends JpaRepository<Keyword, Integer> {

}
