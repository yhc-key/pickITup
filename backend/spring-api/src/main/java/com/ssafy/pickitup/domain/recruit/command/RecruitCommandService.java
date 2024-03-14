package com.ssafy.pickitup.domain.recruit.command;

import com.ssafy.pickitup.domain.recruit.entity.RecruitDocumentElasticsearch;

public interface RecruitCommandService {

    void addKeyword(
        RecruitDocumentElasticsearch recruitDocumentElasticsearch,
        String keyword, String field);
}
