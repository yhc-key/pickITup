package com.ssafy.pickitup.domain.user.command.repository;

import com.ssafy.pickitup.domain.user.entity.UserKeyword;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface UserKeywordCommandJpaRepository extends JpaRepository<UserKeyword, Integer> {

    @Modifying
    @Query(value = "INSERT INTO user_keyword (user_id, keyword_id) VALUES (:userId, :keywordId)", nativeQuery = true)
    void saveUserAndKeyword(int userId, int keywordId);

    default void saveUserAndKeywords(int userId, List<Integer> keywordIds) {
        for (int keywordId : keywordIds) {
            saveUserAndKeyword(userId, keywordId);
        }
    }

    void deleteAllByUserId(int userId);
}
