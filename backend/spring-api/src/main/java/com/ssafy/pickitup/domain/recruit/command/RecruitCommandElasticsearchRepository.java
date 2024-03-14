package com.ssafy.pickitup.domain.recruit.command;

import com.ssafy.pickitup.domain.recruit.entity.RecruitDocumentElasticsearch;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.repository.CrudRepository;

public interface RecruitCommandElasticsearchRepository extends
    ElasticsearchRepository<RecruitDocumentElasticsearch, Integer>,
    CrudRepository<RecruitDocumentElasticsearch, Integer> {

}
