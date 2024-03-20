package com.ssafy.pickitup.domain.quiz.query.repository;

import com.ssafy.pickitup.domain.quiz.entity.OxQuiz;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OxQuizJpaRepository extends JpaRepository<OxQuiz, Integer> {

    List<OxQuiz> findAllByCategory(String category);
}
