package com.ssafy.pickitup.domain.recruit.dao;

import com.ssafy.pickitup.domain.recruit.domain.RecruitingDocumentES;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RecruitingESRepository extends ElasticsearchRepository<RecruitingDocumentES, Integer>, CrudRepository<RecruitingDocumentES, Integer> {
    List<RecruitingDocumentES> findAll();
    @Query("{\"bool\": {\"must\": [{\"match\": {\"qualification_requirements\": \"?0\"}}]}}")
    List<RecruitingDocumentES> findByQualificationRequirementsContaining(String keyword);

    @Query("{\"bool\": {\"must\": [{\"match\": {\"preferred_requirements\": \"?0\"}}]}}")
    List<RecruitingDocumentES> findByPreferredRequirementsContaining(String keyword);
}
