package com.ssafy.pickitup.domain.user.keyword;

import org.springframework.data.jpa.repository.JpaRepository;

public interface KeywordQueryJpaRepository extends JpaRepository<Keyword, Integer> {

    Keyword findKeywordById(int keywordId);
}
