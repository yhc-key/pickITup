package com.ssafy.pickitup.domain.user.query;

import com.ssafy.pickitup.domain.user.entity.UserRecruit;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRecruitQueryJpaRepository extends JpaRepository<UserRecruit, Integer> {

    @Query("SELECT ur FROM UserRecruit ur WHERE ur.user.id = :userId")
    List<UserRecruit> findAllByUserId(Integer userId);

    int countByUserId(Integer userId);
}
