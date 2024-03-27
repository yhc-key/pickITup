package com.ssafy.pickitup.domain.interview.repository;

import com.ssafy.pickitup.domain.interview.entity.Interview;
import java.util.stream.Stream;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InterviewRepository extends JpaRepository<Interview, Integer> {

  Stream<Interview> findBySubCategory(String subCategory);
}
