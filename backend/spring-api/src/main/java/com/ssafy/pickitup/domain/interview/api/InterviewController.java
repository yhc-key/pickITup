package com.ssafy.pickitup.domain.interview.api;

import com.ssafy.pickitup.domain.interview.query.dto.InterviewGameDto;
import com.ssafy.pickitup.domain.interview.query.service.InterviewService;
import com.ssafy.pickitup.global.annotation.AuthID;
import com.ssafy.pickitup.global.api.ApiUtils;
import com.ssafy.pickitup.global.api.ApiUtils.ApiResult;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/interviews")
public class InterviewController {

    private final InterviewService interviewService;

    @GetMapping("/random")
    public ApiResult<List<InterviewGameDto>> randomInterviews(String subCategory) {
        List<InterviewGameDto> interviewsInRandomOrder =
            interviewService.findInterviewsBySubCategoryInRandomOrder(subCategory);
        return ApiUtils.success(interviewsInRandomOrder);
    }

    @GetMapping("/test")
    public String test(@AuthID Integer userId) {
        return "test " + userId;
    }
}
