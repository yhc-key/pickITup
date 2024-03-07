package com.ssafy.pickitup.domain.recruit.service;

import com.ssafy.pickitup.domain.recruit.command.RecruitingCommandMongoRepository;
import com.ssafy.pickitup.domain.recruit.entity.RecruitingDocumentElasticsearch;
import com.ssafy.pickitup.domain.recruit.entity.RecruitingDocumentMongo;
import com.ssafy.pickitup.domain.recruit.exception.InvalidFieldTypeException;
import com.ssafy.pickitup.domain.recruit.query.RecruitingCommandElasticsearchRepository;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RecruitingServiceImpl implements RecruitingService {

    private final RecruitingCommandElasticsearchRepository recruitingCommandElasticsearchRepository;
    private final RecruitingCommandMongoRepository recruitingCommandMongoRepository;

    @Override
    public void readKeywords() {
        try {
            ClassPathResource resource = new ClassPathResource("keywords.txt");
            BufferedReader reader = new BufferedReader(
                new InputStreamReader(resource.getInputStream()));

            // 파일에서 한 줄씩 읽어서 처리
            String line;
            while ((line = reader.readLine()) != null) {
                searchByKeyword(line);
            }
            // 리소스 해제
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<RecruitingDocumentMongo> searchByKeyword(String keyword) {
        Pageable pageable = PageRequest.of(0, 2000);  // 페이지네이션 없이 모든 데이터를 가져오도록 설정

        Page<RecruitingDocumentElasticsearch> qualificationResult = recruitingCommandElasticsearchRepository.findByQualificationRequirementsContaining(
            keyword, pageable);
        List<RecruitingDocumentElasticsearch> qualificationList = qualificationResult.getContent();

        Page<RecruitingDocumentElasticsearch> preferredResult = recruitingCommandElasticsearchRepository.findByPreferredRequirementsContaining(
            keyword, pageable);
        List<RecruitingDocumentElasticsearch> preferredList = preferredResult.getContent();

        for (RecruitingDocumentElasticsearch es : qualificationList) {
            addKeyword(es, keyword, "qualificationRequirements");
        }
        for (RecruitingDocumentElasticsearch es : preferredList) {
            addKeyword(es, keyword, "preferredRequirements");
        }

        return null;
    }

    private RecruitingDocumentMongo addKeyword(
        RecruitingDocumentElasticsearch recruitingDocumentElasticsearch,
        String keyword, String field) {
        RecruitingDocumentMongo mongo = recruitingCommandMongoRepository
            .findById(recruitingDocumentElasticsearch.getId())
            .orElseGet(recruitingDocumentElasticsearch::toMongo);
        Set<String> set;
        switch (field) {
            case "qualificationRequirements" -> set = mongo.getQualificationRequirements();
            case "preferredRequirements" -> set = mongo.getPreferredRequirements();
            default -> throw new InvalidFieldTypeException();
        }

        set.add(keyword);
        return recruitingCommandMongoRepository.save(mongo);
    }
}
