package com.ssafy.pickitup.domain.recruit.dao;

import com.ssafy.pickitup.domain.recruit.domain.RecruitingDocumentMongo;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RecruitingMongoRepository extends
    MongoRepository<RecruitingDocumentMongo, Integer> {

}