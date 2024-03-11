package com.ssafy.pickitup.domain.recruit.api.controller;

import com.ssafy.pickitup.domain.recruit.command.RecruitCommandElasticsearchRepository;
import com.ssafy.pickitup.domain.recruit.query.RecruitQueryService;
import com.ssafy.pickitup.domain.recruit.query.dto.RecruitQueryResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/recruit")
@RestController
@RequiredArgsConstructor
public class RecruitController {

    private final RecruitQueryService recruitQueryService;
    @Autowired
    private RecruitCommandElasticsearchRepository recruitCommandElasticsearchRepository;

    @GetMapping("/{pageNo}")
    public Page<RecruitQueryResponseDto> getAllDocuments(@PathVariable Integer pageNo) {

        return recruitQueryService.searchAll(pageNo);
    }

    @GetMapping("/read")
    public void read() {
        recruitQueryService.readKeywords();
    }
}
