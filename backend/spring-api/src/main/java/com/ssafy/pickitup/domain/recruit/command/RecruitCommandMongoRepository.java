package com.ssafy.pickitup.domain.recruit.command;

import com.ssafy.pickitup.domain.recruit.entity.RecruitDocumentMongo;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RecruitCommandMongoRepository extends
    MongoRepository<RecruitDocumentMongo, Integer> {

}