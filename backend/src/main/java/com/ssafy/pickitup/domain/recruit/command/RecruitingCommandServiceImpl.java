package com.ssafy.pickitup.domain.recruit.command;

import com.ssafy.pickitup.domain.recruit.entity.RecruitingDocumentElasticsearch;
import com.ssafy.pickitup.domain.recruit.entity.RecruitingDocumentMongo;
import com.ssafy.pickitup.domain.recruit.exception.InvalidFieldTypeException;
import com.ssafy.pickitup.domain.recruit.query.RecruitingCommandElasticsearchRepository;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RecruitingCommandServiceImpl implements RecruitingCommandService {

    private final RecruitingCommandElasticsearchRepository recruitingCommandElasticsearchRepository;
    private final RecruitingCommandMongoRepository recruitingCommandMongoRepository;

    @Override
    public void searchByKeyword(String keyword) {
        searchAndAddKeyword(keyword, "qualificationRequirements");
        searchAndAddKeyword(keyword, "preferredRequirements");
    }

    private void searchAndAddKeyword(String keyword, String field) {
        Pageable pageable = PageRequest.of(0, 2000);
        Page<RecruitingDocumentElasticsearch> result = null;

        switch (field) {
            case "qualificationRequirements" ->
                result = recruitingCommandElasticsearchRepository.findByQualificationRequirementsContaining(
                    keyword, pageable);
            case "preferredRequirements" ->
                result = recruitingCommandElasticsearchRepository.findByPreferredRequirementsContaining(
                    keyword, pageable);
            default -> throw new InvalidFieldTypeException();
        }

        List<RecruitingDocumentElasticsearch> list = result.getContent();
        for (RecruitingDocumentElasticsearch es : list) {
            addKeyword(es, keyword, field);
        }
    }

    private void addKeyword(
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
        recruitingCommandMongoRepository.save(mongo);
    }
}
