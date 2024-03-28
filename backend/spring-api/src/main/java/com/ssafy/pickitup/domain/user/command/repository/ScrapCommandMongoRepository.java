package com.ssafy.pickitup.domain.user.command.repository;

import com.ssafy.pickitup.domain.user.entity.ScrapMongo;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ScrapCommandMongoRepository extends
    MongoRepository<ScrapMongo, String> {

    boolean existsByUserIdAndRecruitId(Integer userId, Integer recruitId);

    Optional<ScrapMongo> findByUserIdAndRecruitId(Integer userId, Integer recruitId);
}