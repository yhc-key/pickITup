package com.ssafy.pickitup.domain.recruit.service;

import com.ssafy.pickitup.domain.recruit.domain.RecruitingDocumentES;
import com.ssafy.pickitup.domain.recruit.domain.RecruitingDocumentMongo;
import java.util.List;

public interface RecruitingService {

  void readKeywords();

  List<RecruitingDocumentMongo> searchByKeyword(String keyword);

  RecruitingDocumentMongo addQualification(RecruitingDocumentES recruitingDocumentES,
      String keyword);

  RecruitingDocumentMongo addPreferred(RecruitingDocumentES recruitingDocumentES, String keyword);
}
