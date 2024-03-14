package com.ssafy.pickitup.domain.selfdocument.command;

import com.ssafy.pickitup.domain.selfdocument.entity.SubQuestion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubQuestionCommandJpaRepository extends JpaRepository<SubQuestion, Integer> {

}

