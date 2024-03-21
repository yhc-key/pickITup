package com.ssafy.pickitup.domain.company.query;

import com.ssafy.pickitup.domain.company.entity.CompanyElasticsearch;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.repository.CrudRepository;

public interface CompanyQueryElasticsearchRepository extends
    ElasticsearchRepository<CompanyElasticsearch, Integer>,
    CrudRepository<CompanyElasticsearch, Integer> {

    @Query("{\"bool\": {\"must\": [{\"match\": {\"name\": \"?0\"}}]}}")
    Integer findIdByName(String name);
}
