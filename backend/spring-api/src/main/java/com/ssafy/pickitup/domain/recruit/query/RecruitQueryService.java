package com.ssafy.pickitup.domain.recruit.query;

import com.ssafy.pickitup.domain.recruit.query.dto.RecruitQueryRequestDto;
import com.ssafy.pickitup.domain.recruit.query.dto.RecruitQueryResponseDto;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RecruitQueryService {

    Page<RecruitQueryResponseDto> searchAll(Pageable pageable);

    Page<RecruitQueryResponseDto> search(RecruitQueryRequestDto dto, Pageable pageable);

    void readRecruitForConvert();

    List<RecruitQueryResponseDto> searchByIdList(List<Integer> idList);

    int countClosingRecruitByIdList(List<Integer> idList);
}
