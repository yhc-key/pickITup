package com.ssafy.pickitup.domain.user.query;

import com.ssafy.pickitup.domain.user.entity.UserRecruit;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRecruitQueryJpaRepository extends JpaRepository<UserRecruit, Integer> {

    List<UserRecruit> findAllByUserId(Integer userId);

    int countByUserId(Integer userId);
}
