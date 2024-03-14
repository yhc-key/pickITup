package com.ssafy.pickitup.domain.recruit.query;

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
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RecruitQueryServiceImpl implements RecruitQueryService {

    private final RecruitCommandService recruitCommandService;
    private final RecruitQueryElasticsearchRepository recruitQueryElasticsearchRepository;
    private final RecruitQueryMongoRepository recruitQueryMongoRepository;

    @Override
    public Page<RecruitQueryResponseDto> searchAll(int pageNo) {
        final int pageSize = 6;
        Pageable pageable = PageRequest.of(
            pageNo, pageSize, Sort.by("dueDate").ascending()
        );

        Page<RecruitDocumentMongo> recruitDocumentMongoPages = recruitQueryMongoRepository.findAll(
            pageable);
        return recruitDocumentMongoPages.map(RecruitDocumentMongo::toQueryResponse);
    }

    @Override
    public Page<RecruitQueryResponseDto> search(RecruitQueryRequestDto dto) {
        final int pageSize = 6;
        Pageable pageable = PageRequest.of(
            dto.getPageNo(), pageSize
        );
        StringBuilder sb = new StringBuilder();
        for (String str : dto.getKeywords()) {
            sb.append(str).append(" ");
        }
        return recruitQueryElasticsearchRepository.searchWithFilter(dto.getQuery(), sb.toString(),
                pageable)
            .map(RecruitDocumentElasticsearch::toMongo)
            .map(RecruitDocumentMongo::toQueryResponse);
    }

    @Override
    public void readKeywords() {
        try {
            ClassPathResource resource = new ClassPathResource("keywords.txt");
            BufferedReader reader = new BufferedReader(
                new InputStreamReader(resource.getInputStream()));

            String line;
            while ((line = reader.readLine()) != null) {
                searchByKeyword(line);
            }
            reader.close();
        } catch (FileNotFoundException e) {
            log.error("File 'keywords.txt' not found.", e);
        } catch (IOException e) {
            log.error("An error occurred while reading keywords.", e);
        }
    }

    private void searchByKeyword(String keyword) {
        searchAndAddKeyword(keyword, "qualificationRequirements");
        searchAndAddKeyword(keyword, "preferredRequirements");
    }

    private void searchAndAddKeyword(String keyword, String field) {
        Pageable pageable = PageRequest.of(0, 2000);
        Page<RecruitDocumentElasticsearch> result = null;

        switch (field) {
            case "qualificationRequirements" -> result = recruitQueryElasticsearchRepository
                .findByQualificationRequirementsContaining(keyword, pageable);
            case "preferredRequirements" -> result = recruitQueryElasticsearchRepository
                .findByPreferredRequirementsContaining(keyword, pageable);
            default -> throw new InvalidFieldTypeException();
        }

        List<RecruitDocumentElasticsearch> list = result.getContent();
        for (RecruitDocumentElasticsearch es : list) {
            recruitCommandService.addKeyword(es, keyword, field);
        }
    }

    @Override
    public void test() {
//        String careerText1 = "신입";
//        String careerText2 = "2~7년 경력";
//        String careerText3 = "10년 이상 경력";
//        String careerText4 = "경력무관";
//        String careerText5 = "2023년 졸업예정";
//        String careerText6 = "15-20";
//
//        int[] result1 = parseYearsOfExperienceRange(careerText1);
//        int[] result2 = parseYearsOfExperienceRange(careerText2);
//        int[] result3 = parseYearsOfExperienceRange(careerText3);
//        int[] result4 = parseYearsOfExperienceRange(careerText4);
//        int[] result5 = parseYearsOfExperienceRange(careerText5);
//        int[] result6 = parseYearsOfExperienceRange(careerText6);
//
//        System.out.println("Result 1: " + result1[0] + " ~ " + result1[1]);
//        System.out.println("Result 2: " + result2[0] + " ~ " + result2[1]);
//        System.out.println("Result 3: " + result3[0] + " ~ " + result3[1]);
//        System.out.println("Result 4: " + result4[0] + " ~ " + result4[1]);
//        System.out.println("Result 5: " + result5[0] + " ~ " + result5[1]);
//        System.out.println("Result 6: " + result6[0] + " ~ " + result6[1]);
    }
}
