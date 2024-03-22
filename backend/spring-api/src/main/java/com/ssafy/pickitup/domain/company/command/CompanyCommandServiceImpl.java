package com.ssafy.pickitup.domain.company.command;

import com.ssafy.pickitup.domain.company.entity.CompanyElasticsearch;
import com.ssafy.pickitup.domain.company.entity.CompanyMongo;
import com.ssafy.pickitup.domain.company.query.CompanyQueryMongoRepository;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CompanyCommandServiceImpl implements CompanyCommandService {

    private final CompanyCommandMongoRepository companyCommandMongoRepository;
    private final CompanyQueryMongoRepository companyQueryMongoRepository;

    @Override
    public void convertToMongo(List<CompanyElasticsearch> companyElasticsearchList) {
        for (CompanyElasticsearch companyElasticsearch : companyElasticsearchList) {
            CompanyMongo mongo = companyCommandMongoRepository
                .findById(companyElasticsearch.getId())
                .orElseGet(companyElasticsearch::toMongo);
            companyCommandMongoRepository.save(mongo);
        }
    }

    @Override
    public void addRecruit(CompanyElasticsearch companyElasticsearch, Integer recruitId) {
        CompanyMongo mongo = companyQueryMongoRepository
            .findById(companyElasticsearch.getId())
            .orElseGet(companyElasticsearch::toMongo);

        Set<Integer> recruits = mongo.getRecruits();

        recruits.add(recruitId);
        companyCommandMongoRepository.save(mongo);
    }
}
