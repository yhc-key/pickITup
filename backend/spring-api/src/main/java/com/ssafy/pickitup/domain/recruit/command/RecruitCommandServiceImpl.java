package com.ssafy.pickitup.domain.recruit.command;

import com.ssafy.pickitup.domain.company.query.CompanyQueryService;
import com.ssafy.pickitup.domain.recruit.entity.RecruitDocumentElasticsearch;
import com.ssafy.pickitup.domain.recruit.entity.RecruitDocumentMongo;
import com.ssafy.pickitup.domain.recruit.exception.InvalidFieldTypeException;
import com.ssafy.pickitup.domain.recruit.query.RecruitQueryMongoRepository;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RecruitCommandServiceImpl implements RecruitCommandService {

    private final RecruitCommandMongoRepository recruitCommandMongoRepository;
    private final RecruitQueryMongoRepository recruitQueryMongoRepository;
    private final CompanyQueryService companyQueryService;

    /*
        MongoDB Recruit에 키워드 추가
     */
    @Override
    public void addKeyword(
        RecruitDocumentElasticsearch recruitDocumentElasticsearch,
        String keyword, String field) {

        RecruitDocumentMongo mongo = recruitQueryMongoRepository
            .findById(recruitDocumentElasticsearch.getId())
            .orElseGet(() -> recruitDocumentElasticsearch.toMongo(
                companyQueryService.searchByName(
                    recruitDocumentElasticsearch.getCompany()).getId()));
        Set<String> keywords;
        switch (field) {
            case "qualificationRequirements" -> keywords = mongo.getQualificationRequirements();
            case "preferredRequirements" -> keywords = mongo.getPreferredRequirements();
            default -> throw new InvalidFieldTypeException();
        }

        keywords.add(keyword);
        recruitCommandMongoRepository.save(mongo);
    }
}
