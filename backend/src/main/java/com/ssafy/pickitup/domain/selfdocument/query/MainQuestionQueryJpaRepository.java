package com.ssafy.pickitup.domain.selfdocument.query;

import com.ssafy.pickitup.domain.selfdocument.entity.MainQuestion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MainQuestionQueryJpaRepository extends JpaRepository<MainQuestion, Integer> {

}

