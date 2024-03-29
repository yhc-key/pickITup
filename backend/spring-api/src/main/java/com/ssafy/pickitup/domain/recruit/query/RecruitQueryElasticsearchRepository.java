package com.ssafy.pickitup.domain.recruit.query;

import com.ssafy.pickitup.domain.recruit.entity.RecruitDocumentElasticsearch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.repository.CrudRepository;

public interface RecruitQueryElasticsearchRepository extends
    ElasticsearchRepository<RecruitDocumentElasticsearch, Integer>,
    CrudRepository<RecruitDocumentElasticsearch, Integer> {

    @Query("{\"bool\": {\"must\": [{\"match_phrase\": {\"qualification_requirements\": \"?0\"}}]}}")
    Page<RecruitDocumentElasticsearch> findByQualificationRequirementsContaining(String keyword,
        Pageable pageable);

    @Query("{\"bool\": {\"must\": [{\"match_phrase\": {\"preferred_requirements\": \"?0\"}}]}}")
    Page<RecruitDocumentElasticsearch> findByPreferredRequirementsContaining(String keyword,
        Pageable pageable);

    @Query("{\"bool\": {\"must\": ["
        + "{\"bool\": {\"should\": ["
        + "{\"match\": {\"title\": {\"query\": \"?0\", \"operator\": \"and\"}}}, "
        + "{\"match\": {\"company\": {\"query\": \"?0\", \"operator\": \"and\"}}}, "
        + "{\"match\": {\"qualification_requirements\": {\"query\": \"?0\", \"operator\": \"and\"}}}, "
        + "{\"match\": {\"preferred_requirements\": {\"query\": \"?0\", \"operator\": \"and\"}}}"
        + "], \"minimum_should_match\": 1}}"
        + "]}}")
    Page<RecruitDocumentElasticsearch> searchWithQueryOnly(String query, Pageable pageable);

    @Query("{\"bool\": {\"must\": [{"
        + "\"multi_match\": {\"query\": \"?0\", \"fields\": "
        + "[\"qualification_requirements\", \"preferred_requirements\"]}}"
        + "]}}")
    Page<RecruitDocumentElasticsearch> searchWithKeywordsOnly(String keywords, Pageable pageable);

    @Query("{\"bool\": {\"must\": ["
        + "{\"bool\": {\"should\": ["
        + "{\"match\": {\"title\": {\"query\": \"?0\", \"operator\": \"and\"}}}, "
        + "{\"match\": {\"company\": {\"query\": \"?0\", \"operator\": \"and\"}}}, "
        + "{\"match\": {\"qualification_requirements\": {\"query\": \"?0\", \"operator\": \"and\"}}}, "
        + "{\"match\": {\"preferred_requirements\": {\"query\": \"?0\", \"operator\": \"and\"}}}"
        + "], \"minimum_should_match\": 1}}, "
        + "{\"multi_match\": {\"query\": \"?1\", \"fields\": "
        + "[\"qualification_requirements\", \"preferred_requirements\"]}}"
        + "]}}")
    Page<RecruitDocumentElasticsearch> searchWithFilter(String query, String keywords,
        Pageable pageable);
}
