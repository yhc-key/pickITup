package com.ssafy.pickitup.domain.recruit.query;

import com.ssafy.pickitup.domain.recruit.entity.RecruitingDocumentElasticsearch;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.domain.Pageable;

public interface RecruitingCommandElasticsearchRepository extends
    ElasticsearchRepository<RecruitingDocumentElasticsearch, Integer>,
    CrudRepository<RecruitingDocumentElasticsearch, Integer> {

    List<RecruitingDocumentElasticsearch> findAll();

    @Query("{\"bool\": {\"must\": [{\"match_phrase\": {\"qualification_requirements\": \"?0\"}}]}}")
    Page<RecruitingDocumentElasticsearch> findByQualificationRequirementsContaining(String keyword, Pageable pageable);

    @Query("{\"bool\": {\"must\": [{\"match_phrase\": {\"preferred_requirements\": \"?0\"}}]}}")
    Page<RecruitingDocumentElasticsearch> findByPreferredRequirementsContaining(String keyword, Pageable pageable);
}
