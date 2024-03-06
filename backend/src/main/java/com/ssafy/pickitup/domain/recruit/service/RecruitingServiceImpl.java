package com.ssafy.pickitup.domain.recruit.service;

import com.ssafy.pickitup.domain.recruit.command.RecruitingCommandMongoRepository;
import com.ssafy.pickitup.domain.recruit.entity.RecruitingDocumentElasticsearch;
import com.ssafy.pickitup.domain.recruit.entity.RecruitingDocumentMongo;
import com.ssafy.pickitup.domain.recruit.query.RecruitingCommandElasticsearchRepository;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
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
        List<RecruitingDocumentElasticsearch> qualificationList = recruitingCommandElasticsearchRepository.findByQualificationRequirementsContaining(
            keyword);
        List<RecruitingDocumentElasticsearch> preferredList = recruitingCommandElasticsearchRepository.findByPreferredRequirementsContaining(
            keyword);

        for (RecruitingDocumentElasticsearch es : qualificationList) {
            addQualification(es, keyword);
        }
        for (RecruitingDocumentElasticsearch es : preferredList) {
            addPreferred(es, keyword);
        }

        return null;
    }

    @Override
    public RecruitingDocumentMongo addQualification(
        RecruitingDocumentElasticsearch recruitingDocumentElasticsearch,
        String keyword) {
        RecruitingDocumentMongo mongo = recruitingCommandMongoRepository
            .findById(recruitingDocumentElasticsearch.getId())
            .orElseGet(recruitingDocumentElasticsearch::toMongo);
        Set<String> set = mongo.getQualificationRequirements();

        set.add(keyword);
        mongo.setQualificationRequirements(set);

        return recruitingCommandMongoRepository.save(mongo);
    }

    @Override
    public RecruitingDocumentMongo addPreferred(
        RecruitingDocumentElasticsearch recruitingDocumentElasticsearch,
        String keyword) {
        RecruitingDocumentMongo mongo = recruitingCommandMongoRepository
            .findById(recruitingDocumentElasticsearch.getId())
            .orElseGet(recruitingDocumentElasticsearch::toMongo);
        Set<String> set = mongo.getPreferredRequirements();

        set.add(keyword);
        mongo.setPreferredRequirements(set);

        return recruitingCommandMongoRepository.save(mongo);
    }
}
