package com.ssafy.pickitup.domain.recruit.query;

import com.ssafy.pickitup.domain.recruit.query.dto.RecruitQueryRequestDto;
import com.ssafy.pickitup.domain.recruit.query.dto.RecruitQueryResponseDto;
import org.springframework.data.domain.Page;

public interface RecruitQueryService {

    Page<RecruitQueryResponseDto> searchAll(int pageNo);

    Page<RecruitQueryResponseDto> search(RecruitQueryRequestDto dto);

    void readKeywords();
}
