package com.ssafy.pickitup.user.command;

import com.ssafy.pickitup.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserCommandJpaRepository extends JpaRepository<User, Integer> {

}
