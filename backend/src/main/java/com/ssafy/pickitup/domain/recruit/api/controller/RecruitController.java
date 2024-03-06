package com.ssafy.pickitup.domain.recruit.api.controller;

import com.ssafy.pickitup.domain.recruit.dao.RecruitingESRepository;
import com.ssafy.pickitup.domain.recruit.domain.RecruitingDocumentES;
import com.ssafy.pickitup.domain.recruit.service.RecruitingService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api")
@RestController
@RequiredArgsConstructor
public class RecruitController {

  @Autowired
  private RecruitingESRepository recruitingESRepository;
  private final RecruitingService recruitingService;

  @GetMapping("/allDocuments")
  public List<RecruitingDocumentES> getAllDocuments() {
    // 모든 문서를 검색
    List<RecruitingDocumentES> allDocuments = recruitingESRepository.findAll();

    return allDocuments;
  }

  @GetMapping("/searchByKeyword/{keyword}")
  public List<RecruitingDocumentES> searchByKeyword(@PathVariable String keyword) {
    return recruitingESRepository.findByQualificationRequirementsContaining(keyword);
  }

  @GetMapping("/read")
  public void read() {
    recruitingService.readKeywords();
  }
}
