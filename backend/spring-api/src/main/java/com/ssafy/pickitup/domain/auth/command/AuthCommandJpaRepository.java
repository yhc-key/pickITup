package com.ssafy.pickitup.domain.auth.command;

import com.ssafy.pickitup.domain.auth.entity.Auth;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthCommandJpaRepository extends JpaRepository<Auth, Integer> {

    @Query(value = "SELECT au FROM Auth au WHERE au.username = :username")
    Auth findAuthByUsername(@Param("username") String username);

    Auth findAuthById(int id);

    @Query(value = "SELECT * FROM auth au WHERE au.id = :authId AND au.is_deleted=true", nativeQuery = true)
    Optional<Auth> findDeletedAuthById(@Param("authId") Integer authId);

    Optional<Auth> findByProviderAndProviderId(String provider, String providerId);

}
