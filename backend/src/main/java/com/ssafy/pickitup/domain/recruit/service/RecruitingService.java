package com.ssafy.pickitup.domain.recruit.service;

import com.ssafy.pickitup.domain.recruit.entity.RecruitingDocumentElasticsearch;
import com.ssafy.pickitup.domain.recruit.entity.RecruitingDocumentMongo;
import java.util.List;

public interface RecruitingService {

    void readKeywords();

    List<RecruitingDocumentMongo> searchByKeyword(String keyword);

    RecruitingDocumentMongo addQualification(
        RecruitingDocumentElasticsearch recruitingDocumentElasticsearch,
        String keyword);

    RecruitingDocumentMongo addPreferred(
        RecruitingDocumentElasticsearch recruitingDocumentElasticsearch, String keyword);
}
