package com.ssafy.pickitup.domain.recruit.query;

import com.ssafy.pickitup.domain.recruit.entity.RecruitDocumentMongo;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RecruitQueryMongoRepository extends
    MongoRepository<RecruitDocumentMongo, Integer> {

    Page<RecruitDocumentMongo> findByDueDateAfter(LocalDate today, Pageable pageable);

    List<RecruitDocumentMongo> findByIdIn(List<Integer> ids);
}