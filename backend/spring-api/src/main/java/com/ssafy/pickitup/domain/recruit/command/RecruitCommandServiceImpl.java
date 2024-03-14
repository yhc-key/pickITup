package com.ssafy.pickitup.domain.recruit.command;

import com.ssafy.pickitup.domain.recruit.entity.RecruitDocumentElasticsearch;
import com.ssafy.pickitup.domain.recruit.entity.RecruitDocumentMongo;
import com.ssafy.pickitup.domain.recruit.exception.InvalidFieldTypeException;
import com.ssafy.pickitup.domain.recruit.query.RecruitQueryElasticsearchRepository;
import com.ssafy.pickitup.domain.recruit.query.RecruitQueryMongoRepository;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RecruitCommandServiceImpl implements RecruitCommandService {

    private final RecruitQueryElasticsearchRepository recruitQueryElasticsearchRepository;
    private final RecruitCommandMongoRepository recruitCommandMongoRepository;
    private final RecruitQueryMongoRepository recruitQueryMongoRepository;

    @Override
    public void addKeyword(
        RecruitDocumentElasticsearch recruitDocumentElasticsearch,
        String keyword, String field) {
        RecruitDocumentMongo mongo = recruitQueryMongoRepository
            .findById(recruitDocumentElasticsearch.getId())
            .orElseGet(recruitDocumentElasticsearch::toMongo);
        Set<String> set;
        switch (field) {
            case "qualificationRequirements" -> set = mongo.getQualificationRequirements();
            case "preferredRequirements" -> set = mongo.getPreferredRequirements();
            default -> throw new InvalidFieldTypeException();
        }

        set.add(keyword);
        recruitCommandMongoRepository.save(mongo);
    }
}
