package com.ssafy.pickitup.domain.user.command.repository;

import com.ssafy.pickitup.domain.user.entity.ClickMongo;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ClickCommandMongoRepository extends
    MongoRepository<ClickMongo, String> {

    Optional<ClickMongo> findByUserIdAndRecruitId(Integer userId, Integer recruitId);
}