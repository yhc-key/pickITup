package com.ssafy.pickitup.domain.company.query;

import com.ssafy.pickitup.domain.company.entity.CompanyMongo;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CompanyQueryMongoRepository extends MongoRepository<CompanyMongo, Integer> {

}