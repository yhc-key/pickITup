package com.ssafy.pickitup.domain.quiz.query.repository;

import com.ssafy.pickitup.domain.quiz.entity.SpeedQuiz;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpeedQuizJpaRepository extends JpaRepository<SpeedQuiz, Integer> {

    List<SpeedQuiz> findAllByCategory(String category);
}
