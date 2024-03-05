package com.ssafy.pickitup.domain.recruit.dao;

import com.ssafy.pickitup.domain.recruit.domain.RecruitingDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RecruitingDocumentRepository extends ElasticsearchRepository<RecruitingDocument, Long>, CrudRepository<RecruitingDocument, Long> {
    List<RecruitingDocument> findAll();
    List<RecruitingDocument> findByQualificationRequirementsContaining(String keyword);
}
