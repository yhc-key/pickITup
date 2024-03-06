package com.ssafy.pickitup.domain.recruit.service;

import com.ssafy.pickitup.domain.recruit.dao.RecruitingESRepository;
import com.ssafy.pickitup.domain.recruit.dao.RecruitingMongoRepository;
import com.ssafy.pickitup.domain.recruit.domain.RecruitingDocumentES;
import com.ssafy.pickitup.domain.recruit.domain.RecruitingDocumentMongo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class RecruitingServiceImpl implements RecruitingService {
    private final RecruitingESRepository recruitingESRepository;
    private final RecruitingMongoRepository recruitingMongoRepository;
    @Override
    public void readKeywords() {
        try {
            ClassPathResource resource = new ClassPathResource("keywords.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream()));

            // 파일에서 한 줄씩 읽어서 처리
            String line;
            while ((line = reader.readLine()) != null) {
                searchByKeyword(line);
            }
            // 리소스 해제
            reader.close();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public List<RecruitingDocumentMongo> searchByKeyword(String keyword) {
        List<RecruitingDocumentES> qualificationList = recruitingESRepository.findByQualificationRequirementsContaining(keyword);
        List<RecruitingDocumentES> preferredList = recruitingESRepository.findByPreferredRequirementsContaining(keyword);

        for(RecruitingDocumentES es : qualificationList){
            addQualification(es, keyword);
        }
        for(RecruitingDocumentES es : preferredList){
            addPreferred(es, keyword);
        }

        return null;
    }

    @Override
    public RecruitingDocumentMongo addQualification(RecruitingDocumentES recruitingDocumentES, String keyword) {
        RecruitingDocumentMongo mongo = recruitingMongoRepository
                .findById(recruitingDocumentES.getId())
                .orElseGet(recruitingDocumentES::toMongo);
        Set<String> set = mongo.getQualificationRequirements();

        set.add(keyword);
        mongo.setQualificationRequirements(set);

        return recruitingMongoRepository.save(mongo);
    }

    @Override
    public RecruitingDocumentMongo addPreferred(RecruitingDocumentES recruitingDocumentES, String keyword) {
        RecruitingDocumentMongo mongo = recruitingMongoRepository
                .findById(recruitingDocumentES.getId())
                .orElseGet(recruitingDocumentES::toMongo);
        Set<String> set = mongo.getPreferredRequirements();

        set.add(keyword);
        mongo.setPreferredRequirements(set);

        return recruitingMongoRepository.save(mongo);
    }
}
