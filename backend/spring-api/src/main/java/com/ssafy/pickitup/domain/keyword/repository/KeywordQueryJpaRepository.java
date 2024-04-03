package com.ssafy.pickitup.domain.keyword.repository;

import com.ssafy.pickitup.domain.keyword.entity.Keyword;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KeywordQueryJpaRepository extends JpaRepository<Keyword, Integer> {

}
