package com.ssafy.pickitup.domain.recruit.api.controller;

import com.ssafy.pickitup.domain.recruit.entity.RecruitingDocumentElasticsearch;
import com.ssafy.pickitup.domain.recruit.query.RecruitingCommandElasticsearchRepository;
import com.ssafy.pickitup.domain.recruit.query.RecruitingQueryService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api")
@RestController
@RequiredArgsConstructor
public class RecruitController {

    @Autowired
    private RecruitingCommandElasticsearchRepository recruitingCommandElasticsearchRepository;
    private final RecruitingQueryService recruitingQueryService;

    @GetMapping("/allDocuments")
    public List<RecruitingDocumentElasticsearch> getAllDocuments() {
        // 모든 문서를 검색
        List<RecruitingDocumentElasticsearch> allDocuments = recruitingCommandElasticsearchRepository.findAll();

        return allDocuments;
    }

//    @GetMapping("/searchByKeyword/{keyword}")
//    public List<RecruitingDocumentElasticsearch> searchByKeyword(@PathVariable String keyword) {
//        return recruitingCommandElasticsearchRepository.findByQualificationRequirementsContaining(
//            keyword);
//    }

    @GetMapping("/read")
    public void read() {
        recruitingQueryService.readKeywords();
    }
}
