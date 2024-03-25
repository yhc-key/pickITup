package com.ssafy.pickitup.domain.user.query;

import com.ssafy.pickitup.domain.user.entity.UserKeyword;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserKeywordQueryJpaRepository extends JpaRepository<UserKeyword, Integer> {

    List<UserKeyword> findAllByUserId(int authId);
}
