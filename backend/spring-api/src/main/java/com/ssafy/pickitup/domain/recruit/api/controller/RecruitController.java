package com.ssafy.pickitup.domain.recruit.api.controller;

import com.ssafy.pickitup.domain.recruit.command.RecruitCommandElasticsearchRepository;
import com.ssafy.pickitup.domain.recruit.query.RecruitQueryService;
import com.ssafy.pickitup.domain.recruit.query.dto.RecruitQueryRequestDto;
import com.ssafy.pickitup.domain.recruit.query.dto.RecruitQueryResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = {"https://pickitup.online", "http://localhost:3000", "http://localhost:8080",
    "https://spring.pickitup.online"}, exposedHeaders = "*")
@RequestMapping("/recruit")
@RestController
@RequiredArgsConstructor
@Tag(name = "RecruitController", description = "채용공고 관련 API")
public class RecruitController {

    private final RecruitQueryService recruitQueryService;
    @Autowired
    private RecruitCommandElasticsearchRepository recruitCommandElasticsearchRepository;

    @Operation(summary = "채용 공고 조회(전체)")
    @GetMapping("/{pageNo}")
    public Page<RecruitQueryResponseDto> getAllDocuments(@PathVariable Integer pageNo) {

        return recruitQueryService.searchAll(pageNo);
    }

    @Operation(summary = "채용 공고 조회(키워드, 검색어)")
    @PostMapping("/search")
    public Page<RecruitQueryResponseDto> getDocuments(@RequestBody RecruitQueryRequestDto dto) {
        return recruitQueryService.search(dto);
    }

    @Operation(summary = "Elasticsearch 데이터를 mongodb로 마이그레이션")
    @GetMapping("/read")
    public void read() {
        recruitQueryService.readKeywords();
    }
}
