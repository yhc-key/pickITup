package com.ssafy.pickitup.domain.auth.query;

import com.ssafy.pickitup.domain.auth.entity.Auth;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthQueryJpaRepository extends JpaRepository<Auth, Integer> {

    Auth findAuthById(int id);
}
