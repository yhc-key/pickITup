package com.ssafy.pickitup.domain.selfdocument.query;

import com.ssafy.pickitup.domain.selfdocument.entity.SubQuestion;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubQuestionQueryJpaRepository extends JpaRepository<SubQuestion, Integer> {

    List<SubQuestion> findByMainQuestionId(Integer mainId);
}

