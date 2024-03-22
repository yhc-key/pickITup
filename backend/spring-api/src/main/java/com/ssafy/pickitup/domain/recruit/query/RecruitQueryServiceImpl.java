package com.ssafy.pickitup.domain.recruit.query;

import com.ssafy.pickitup.domain.company.command.CompanyCommandService;
import com.ssafy.pickitup.domain.company.entity.CompanyElasticsearch;
import com.ssafy.pickitup.domain.company.query.CompanyQueryService;
import com.ssafy.pickitup.domain.recruit.command.RecruitCommandService;
import com.ssafy.pickitup.domain.recruit.entity.RecruitDocumentElasticsearch;
import com.ssafy.pickitup.domain.recruit.entity.RecruitDocumentMongo;
import com.ssafy.pickitup.domain.recruit.exception.InvalidFieldTypeException;
import com.ssafy.pickitup.domain.recruit.query.dto.RecruitQueryRequestDto;
import com.ssafy.pickitup.domain.recruit.query.dto.RecruitQueryResponseDto;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RecruitQueryServiceImpl implements RecruitQueryService {

    private final MongoTemplate mongoTemplate;

    private final RecruitCommandService recruitCommandService;
    private final RecruitQueryElasticsearchRepository recruitQueryElasticsearchRepository;
    private final RecruitQueryMongoRepository recruitQueryMongoRepository;
    private final CompanyCommandService companyCommandService;
    private final CompanyQueryService companyQueryService;

    @Override
    public Page<RecruitQueryResponseDto> searchAll(Pageable pageable) {

        Pageable new_pageable = PageRequest.of(
            pageable.getPageNumber(), pageable.getPageSize(), Sort.by("dueDate").ascending()
        );

        Page<RecruitDocumentMongo> recruitDocumentMongoPages =
            recruitQueryMongoRepository.findAll(new_pageable);
        return recruitDocumentMongoPages.map(RecruitDocumentMongo::toQueryResponse);
    }

    /*
        키워드들과 검색어로 검색
     */
    @Override
    public Page<RecruitQueryResponseDto> search(RecruitQueryRequestDto dto, Pageable pageable) {
        Pageable new_pageable = PageRequest.of(
            pageable.getPageNumber(), pageable.getPageSize(), Sort.by("dueDate").ascending()
        );
        StringBuilder sb = new StringBuilder();
        for (String str : dto.getKeywords()) {
            sb.append(str).append(" ");
        }
        return recruitQueryElasticsearchRepository.searchWithFilter(
                dto.getQuery(), sb.toString(), new_pageable)
            .map(RecruitDocumentElasticsearch::toMongo)
            .map(RecruitDocumentMongo::toQueryResponse);
    }

    @Override
    public void readRecruitForConvert() {
        List<String> keywords = readKeywords();
        for (String keyword : keywords) {
            searchByKeyword(keyword);
        }
    }


    @Override
    public Page<RecruitQueryResponseDto> searchByIdList(List<Integer> idList, Pageable pageable) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").in(idList));

        long totalCount = mongoTemplate.count(query, RecruitDocumentMongo.class);
        query.with(pageable);

        List<RecruitDocumentMongo> entities = mongoTemplate.find(query, RecruitDocumentMongo.class);
        Page<RecruitDocumentMongo> recruitDocumentMongoPages =
            new PageImpl<>(entities, pageable, totalCount);
        return recruitDocumentMongoPages.map(RecruitDocumentMongo::toQueryResponse);
    }

    /*
        keywords 파일에서 키워드 추출
     */
    private List<String> readKeywords() {
        List<String> keywords = new ArrayList<>();
        try {
            ClassPathResource resource = new ClassPathResource("keywords.txt");
            BufferedReader reader = new BufferedReader(
                new InputStreamReader(resource.getInputStream()));

            String line;
            while ((line = reader.readLine()) != null) {
                keywords.add(line);
            }
            reader.close();
        } catch (FileNotFoundException e) {
            log.error("File 'keywords.txt' not found.", e);
        } catch (IOException e) {
            log.error("An error occurred while reading keywords.", e);
        }
        return keywords;
    }

    /*
        추출된 키워드로 자격요건, 우대사항 별로 탐색
     */
    private void searchByKeyword(String keyword) {
        searchAndAddKeyword(keyword, "qualificationRequirements");
        searchAndAddKeyword(keyword, "preferredRequirements");
    }

    /*
        Elasticsearch에서 키워드 검색
     */
    private void searchAndAddKeyword(String keyword, String field) {
//        Pageable pageable = PageRequest.of(0, 2000);
        Page<RecruitDocumentElasticsearch> result = null;

        switch (field) {
            case "qualificationRequirements" -> result = recruitQueryElasticsearchRepository
                .findByQualificationRequirementsContaining(keyword, Pageable.unpaged());
//                .findByQualificationRequirementsContaining(keyword, pageable);
            case "preferredRequirements" -> result = recruitQueryElasticsearchRepository
                .findByPreferredRequirementsContaining(keyword, Pageable.unpaged());
//                .findByPreferredRequirementsContaining(keyword, pageable);
            default -> throw new InvalidFieldTypeException();
        }

        List<RecruitDocumentElasticsearch> list = result.getContent();
        for (RecruitDocumentElasticsearch es : list) {
            CompanyElasticsearch companyElasticsearch =
                companyQueryService.searchByName(es.getCompany());
            companyCommandService.addRecruit(companyElasticsearch, es.getId());
            recruitCommandService.addKeyword(es, keyword, field);
        }
    }
}
