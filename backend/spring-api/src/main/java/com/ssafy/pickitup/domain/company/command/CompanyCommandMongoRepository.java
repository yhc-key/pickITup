package com.ssafy.pickitup.domain.company.command;

import com.ssafy.pickitup.domain.company.entity.CompanyMongo;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CompanyCommandMongoRepository extends
    MongoRepository<CompanyMongo, Integer> {

}