package com.ssafy.pickitup.domain.company.command;

import com.ssafy.pickitup.domain.company.entity.CompanyElasticsearch;
import com.ssafy.pickitup.domain.company.entity.CompanyMongo;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CompanyCommandServiceImpl implements CompanyCommandService {

    private final CompanyCommandMongoRepository companyCommandMongoRepository;

    @Override
    public void convertToMongo(List<CompanyElasticsearch> companyElasticsearchList) {
        for (CompanyElasticsearch companyElasticsearch : companyElasticsearchList) {
            CompanyMongo mongo = companyCommandMongoRepository
                .findById(companyElasticsearch.getId())
                .orElseGet(companyElasticsearch::toMongo);
            companyCommandMongoRepository.save(mongo);
        }
    }
}
