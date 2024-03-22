package com.ssafy.pickitup.domain.company.query;

import com.ssafy.pickitup.domain.company.command.CompanyCommandService;
import com.ssafy.pickitup.domain.company.entity.CompanyElasticsearch;
import com.ssafy.pickitup.domain.company.exception.CompanyNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CompanyQueryServiceImpl implements CompanyQueryService {

    private final CompanyQueryElasticsearchRepository companyQueryElasticsearchRepository;
    private final CompanyCommandService companyCommandService;

    @Override
    public void readCompanyForConvert() {
        List<CompanyElasticsearch> companyElasticsearchList =
            companyQueryElasticsearchRepository.findAll(Pageable.unpaged()).getContent();
        companyCommandService.convertToMongo(companyElasticsearchList);
    }

    @Override
    public CompanyElasticsearch searchByName(String name) {
        return companyQueryElasticsearchRepository.findIdByName(name)
            .orElseThrow(CompanyNotFoundException::new);
    }
}
