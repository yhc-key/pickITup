package com.ssafy.pickitup.user.query;

import com.ssafy.pickitup.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserQueryJpaRepository extends JpaRepository<User, Integer> {

  User findById(int id);
}
