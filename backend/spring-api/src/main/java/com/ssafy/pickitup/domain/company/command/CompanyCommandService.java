package com.ssafy.pickitup.domain.company.command;

import com.ssafy.pickitup.domain.company.entity.CompanyElasticsearch;
import java.util.List;

public interface CompanyCommandService {

    void convertToMongo(List<CompanyElasticsearch> companyElasticsearchList);
}
