package com.ssafy.pickitup.domain.recruit.query;

import com.ssafy.pickitup.domain.recruit.entity.RecruitDocumentMongo;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RecruitQueryMongoRepository extends
    MongoRepository<RecruitDocumentMongo, Integer> {

    List<RecruitDocumentMongo> findByIdIn(List<Integer> ids);
}