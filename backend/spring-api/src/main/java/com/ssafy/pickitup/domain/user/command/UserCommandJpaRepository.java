package com.ssafy.pickitup.domain.user.command;

import com.ssafy.pickitup.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserCommandJpaRepository extends JpaRepository<User, Integer> {

    //    @Query(value = "SELECT * FROM user u WHERE u.auth_id = :authId", nativeQuery = true)
    User findByAuthId(Integer authId);


}
