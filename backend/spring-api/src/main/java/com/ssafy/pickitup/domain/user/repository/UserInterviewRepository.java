package com.ssafy.pickitup.domain.user.repository;

import com.ssafy.pickitup.domain.user.entity.UserInterview;
import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserInterviewRepository extends JpaRepository<UserInterview, Integer> {

  @EntityGraph(attributePaths = {"interview"})
  Stream<UserInterview> findByUserIdOrderByLastModifiedDateDesc(Integer userId);

  Optional<UserInterview> findByUserIdAndInterviewId(Integer userId, Integer interviewId);
}
