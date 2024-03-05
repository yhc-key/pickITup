package com.ssafy.pickitup.domain.recruit.api;

import com.ssafy.pickitup.domain.recruit.dao.RecruitingDocumentRepository;
import com.ssafy.pickitup.domain.recruit.domain.RecruitingDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/api")
@RestController
public class RecruitController {
    @Autowired
    private RecruitingDocumentRepository recruitingDocumentRepository;

    @GetMapping("/allDocuments")
    public List<RecruitingDocument> getAllDocuments() {
        // 모든 문서를 검색
        List<RecruitingDocument> allDocuments = recruitingDocumentRepository.findAll();

        return allDocuments;
    }
}
