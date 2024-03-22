package com.ssafy.pickitup.domain.company.query;

import com.ssafy.pickitup.domain.company.entity.CompanyElasticsearch;

public interface CompanyQueryService {

    void readCompanyForConvert();

    CompanyElasticsearch searchByName(String name);
}
