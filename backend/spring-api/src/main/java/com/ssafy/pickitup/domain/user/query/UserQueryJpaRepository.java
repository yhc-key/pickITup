package com.ssafy.pickitup.domain.user.query;

import com.ssafy.pickitup.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserQueryJpaRepository extends JpaRepository<User, Integer> {

}
