package com.ssafy.pickitup.domain.auth.query;

import com.ssafy.pickitup.domain.auth.entity.Auth;
import com.ssafy.pickitup.domain.auth.query.dto.AuthDto;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthQueryJpaRepository extends JpaRepository<Auth, Integer> {

    Auth findAuthById(int id);

    Optional<Auth> findAuthByUsername(String username);
}
