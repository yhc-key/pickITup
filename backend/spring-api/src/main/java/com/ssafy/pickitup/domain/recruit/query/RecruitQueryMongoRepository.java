package com.ssafy.pickitup.domain.recruit.query;

import com.ssafy.pickitup.domain.recruit.entity.RecruitDocumentMongo;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RecruitQueryMongoRepository extends
    MongoRepository<RecruitDocumentMongo, Integer> {

}