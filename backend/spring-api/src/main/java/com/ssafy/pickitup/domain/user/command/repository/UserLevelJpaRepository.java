package com.ssafy.pickitup.domain.user.command.repository;

import com.ssafy.pickitup.domain.user.entity.UserLevel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserLevelJpaRepository extends JpaRepository<UserLevel, Integer> {

    UserLevel findFirstByExpGreaterThanOrderByExpAsc(int exp);

    @Query("SELECT u FROM UserLevel u WHERE u.level = :level")
    UserLevel findExpByLevel(@Param("level") int level);
}
