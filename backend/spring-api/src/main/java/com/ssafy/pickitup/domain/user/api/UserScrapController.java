package com.ssafy.pickitup.domain.user.api;

import static com.ssafy.pickitup.global.api.ApiUtils.success;

import com.ssafy.pickitup.domain.recruit.query.dto.RecruitQueryResponseDto;
import com.ssafy.pickitup.domain.user.command.service.UserCommandService;
import com.ssafy.pickitup.domain.user.query.UserQueryService;
import com.ssafy.pickitup.global.annotation.AuthID;
import com.ssafy.pickitup.global.api.ApiUtils.ApiResult;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = {"https://pickitup.online", "http://localhost:3000",
    "http://localhost:8080", "https://spring.pickitup.online"}, exposedHeaders = "*")
@RequestMapping("/users/scraps")
public class UserScrapController {

    private final UserCommandService userCommandService;
    private final UserQueryService userQueryService;

    @Operation(summary = "회원 스크랩 채용 공고 조회 API")
    @GetMapping("/recruit")
    public ApiResult<?> getUserScrapList(
        @AuthID Integer userId) {
        List<RecruitQueryResponseDto> myRecruitByIdList =
            userQueryService.findMyRecruitById(userId);
        return success(myRecruitByIdList);
    }

    @Operation(summary = "회원 채용 공고 스크랩 API")
    @PostMapping("/recruit")
    public ApiResult<?> saveUserScrapList(@AuthID Integer userId, @RequestParam Integer recruitId) {
        userCommandService.saveUserRecruit(userId, recruitId);
        return success("스크랩 성공");
    }

    @Operation(summary = "회원 채용 공고 스크랩 삭제 API")
    @DeleteMapping("/recruit")
    public ApiResult<?> deleteUserScrap(@AuthID Integer userId, @RequestParam Integer recruitId) {
        userCommandService.deleteUserRecruit(userId, recruitId);
        return success("스크랩 삭제");
    }
}
