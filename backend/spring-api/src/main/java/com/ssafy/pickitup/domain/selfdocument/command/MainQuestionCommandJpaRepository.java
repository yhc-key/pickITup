package com.ssafy.pickitup.domain.selfdocument.command;

import com.ssafy.pickitup.domain.selfdocument.entity.MainQuestion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MainQuestionCommandJpaRepository extends JpaRepository<MainQuestion, Integer> {

}

