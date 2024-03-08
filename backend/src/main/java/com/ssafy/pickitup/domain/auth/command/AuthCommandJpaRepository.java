package com.ssafy.pickitup.domain.auth.command;

import com.ssafy.pickitup.domain.auth.entity.Auth;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthCommandJpaRepository extends JpaRepository<Auth, Integer> {

}
