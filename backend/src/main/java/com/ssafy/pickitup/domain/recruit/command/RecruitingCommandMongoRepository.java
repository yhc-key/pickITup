package com.ssafy.pickitup.domain.recruit.command;

import com.ssafy.pickitup.domain.recruit.entity.RecruitingDocumentMongo;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RecruitingCommandMongoRepository extends
    MongoRepository<RecruitingDocumentMongo, Integer> {

}