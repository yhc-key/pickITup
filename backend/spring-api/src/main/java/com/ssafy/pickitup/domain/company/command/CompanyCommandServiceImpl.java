package com.ssafy.pickitup.domain.company.command;

import com.ssafy.pickitup.domain.company.entity.CompanyElasticsearch;
import com.ssafy.pickitup.domain.company.entity.CompanyMongo;
import com.ssafy.pickitup.domain.company.query.CompanyQueryMongoRepository;
import com.ssafy.pickitup.global.service.GeoLocationService;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CompanyCommandServiceImpl implements CompanyCommandService {

    private final CompanyCommandMongoRepository companyCommandMongoRepository;
    private final CompanyQueryMongoRepository companyQueryMongoRepository;
    private final GeoLocationService geoLocationService;

    @Override
    public void convertToMongo(List<CompanyElasticsearch> companyElasticsearchList) {
        for (CompanyElasticsearch companyElasticsearch : companyElasticsearchList) {
            CompanyMongo mongo = companyCommandMongoRepository
                .findById(companyElasticsearch.getId())
                .orElseGet(() -> companyElasticsearch.toMongo(
                    geoLocationService.getGeoLocation(companyElasticsearch.getAddress())));
            companyCommandMongoRepository.save(mongo);
        }
    }

    @Override
    public void addRecruit(CompanyElasticsearch companyElasticsearch, Integer recruitId) {
        CompanyMongo mongo = companyQueryMongoRepository
            .findById(companyElasticsearch.getId())
            .orElseGet(() -> companyElasticsearch.toMongo(
                geoLocationService.getGeoLocation(companyElasticsearch.getAddress())));

        Set<Integer> recruits = mongo.getRecruits();

        recruits.add(recruitId);
        companyCommandMongoRepository.save(mongo);
    }
}
