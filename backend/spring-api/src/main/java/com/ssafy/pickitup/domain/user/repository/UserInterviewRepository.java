package com.ssafy.pickitup.domain.user.repository;

import com.ssafy.pickitup.domain.user.entity.UserInterview;
import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserInterviewRepository extends JpaRepository<UserInterview, Integer> {

  @EntityGraph(attributePaths = {"interview"})
  @Query("SELECT ui FROM UserInterview ui WHERE ui.user.id = :userId ORDER BY ui.lastModifiedDate DESC")
  Stream<UserInterview> findByUserIdOrderByLastModifiedDateDesc(Integer userId);

  int countByUserId(Integer userId);

  Optional<UserInterview> findByUserIdAndInterviewId(Integer userId, Integer interviewId);
}
