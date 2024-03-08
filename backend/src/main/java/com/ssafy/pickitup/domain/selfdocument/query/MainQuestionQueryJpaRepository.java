package com.ssafy.pickitup.domain.selfdocument.query;

import com.ssafy.pickitup.domain.selfdocument.entity.MainQuestion;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MainQuestionQueryJpaRepository extends JpaRepository<MainQuestion, Integer> {

    List<MainQuestion> findByUserId(Integer userId);
}

