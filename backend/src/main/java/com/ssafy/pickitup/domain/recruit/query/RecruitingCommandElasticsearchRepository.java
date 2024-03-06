package com.ssafy.pickitup.domain.recruit.query;

import com.ssafy.pickitup.domain.recruit.entity.RecruitingDocumentElasticsearch;
import java.util.List;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.repository.CrudRepository;

public interface RecruitingCommandElasticsearchRepository extends
    ElasticsearchRepository<RecruitingDocumentElasticsearch, Integer>,
    CrudRepository<RecruitingDocumentElasticsearch, Integer> {

    List<RecruitingDocumentElasticsearch> findAll();

    @Query("{\"bool\": {\"must\": [{\"match\": {\"qualification_requirements\": \"?0\"}}]}}")
    List<RecruitingDocumentElasticsearch> findByQualificationRequirementsContaining(String keyword);

    @Query("{\"bool\": {\"must\": [{\"match\": {\"preferred_requirements\": \"?0\"}}]}}")
    List<RecruitingDocumentElasticsearch> findByPreferredRequirementsContaining(String keyword);
}
