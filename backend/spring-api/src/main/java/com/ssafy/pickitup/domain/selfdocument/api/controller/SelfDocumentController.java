package com.ssafy.pickitup.domain.selfdocument.api.controller;

import static com.ssafy.pickitup.domain.auth.api.ApiUtils.success;

import com.ssafy.pickitup.domain.auth.api.ApiUtils.ApiResult;
import com.ssafy.pickitup.domain.selfdocument.command.MainQuestionCommandService;
import com.ssafy.pickitup.domain.selfdocument.command.SubQuestionCommandService;
import com.ssafy.pickitup.domain.selfdocument.command.dto.MainQuestionCommandRequestDto;
import com.ssafy.pickitup.domain.selfdocument.command.dto.MainQuestionCommandResponseDto;
import com.ssafy.pickitup.domain.selfdocument.command.dto.SubQuestionCommandRequestDto;
import com.ssafy.pickitup.domain.selfdocument.command.dto.SubQuestionCommandResponseDto;
import com.ssafy.pickitup.domain.selfdocument.query.MainQuestionQueryService;
import com.ssafy.pickitup.domain.selfdocument.query.SubQuestionQueryService;
import com.ssafy.pickitup.domain.selfdocument.query.dto.MainQuestionQueryResponseDto;
import com.ssafy.pickitup.domain.selfdocument.query.dto.SubQuestionQueryResponseDto;
import com.ssafy.pickitup.security.jwt.JwtTokenProvider;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = {"https://pickitup.online", "http://localhost:3000", "http://localhost:8080",
    "https://spring.pickitup.online"}, exposedHeaders = "*")
@RequestMapping("/self")
@RestController
@RequiredArgsConstructor
@Tag(name = "SelfDocumentController", description = "자기소개서 관련 API")
public class SelfDocumentController {

    private final MainQuestionQueryService mainQueryService;
    private final MainQuestionCommandService mainCommandService;
    private final SubQuestionQueryService subQueryService;
    private final SubQuestionCommandService subCommandService;
    private final JwtTokenProvider jwtTokenProvider;

    @Operation(summary = "메인 질문 조회")
    @GetMapping("/main")
    public ApiResult<List<MainQuestionQueryResponseDto>> searchMain(
        @RequestHeader(HttpHeaders.AUTHORIZATION) String accessToken) {
        int authId = Integer.parseInt(jwtTokenProvider.extractAuthId(accessToken));
        List<MainQuestionQueryResponseDto> mainQuestionQueryResponseDtoList = mainQueryService.searchMainQuestions(
            authId);
        return success(mainQuestionQueryResponseDtoList);
    }

    @Operation(summary = "메인 질문 등록")
    @PostMapping("/main")
    public ApiResult<MainQuestionCommandResponseDto> registerMain(
        @RequestBody MainQuestionCommandRequestDto dto,
        @RequestHeader(HttpHeaders.AUTHORIZATION) String accessToken) {
        int authId = Integer.parseInt(jwtTokenProvider.extractAuthId(accessToken));
        MainQuestionCommandResponseDto registeredMainQuestionDto = mainCommandService.registerMainQuestion(
            authId, dto);
        return success(registeredMainQuestionDto);
    }


    @Operation(summary = "메인 질문 삭제")
    @DeleteMapping("/main/{mainId}")
    public ApiResult<?> deleteMain(@PathVariable Integer mainId) {
        boolean result = mainCommandService.deleteMainQuestion(mainId);
        return success(result);
//        return mainCommandService.deleteMainQuestion(mainId)
//            ? ResponseEntity.ok().build() : ResponseEntity.badRequest().build();
    }


    @Operation(summary = "메인 질문 수정")
    @PatchMapping("/main/{mainId}")
    public ApiResult<?> patchMain(@PathVariable Integer mainId,
        @RequestBody MainQuestionCommandRequestDto dto) {
        MainQuestionCommandResponseDto mainQuestionCommandResponseDto = mainCommandService.modifyMainQuestion(
            mainId, dto);
        return success(mainQuestionCommandResponseDto);
//        return ResponseEntity.ok(mainCommandService.modifyMainQuestion(mainId, dto));
    }


    @Operation(summary = "서브 질문 조회")
    @GetMapping("/main/{mainId}/sub")
    public ApiResult<?> searchSub(@PathVariable Integer mainId) {
        List<SubQuestionQueryResponseDto> subQuestionQueryResponseDtoList = subQueryService.searchSubQuestions(
            mainId);
        return success(subQuestionQueryResponseDtoList);
//        return ResponseEntity.ok(subQueryService.searchSubQuestions(mainId));
    }

    @Operation(summary = "서브 질문 등록")
    @PostMapping("/main/{mainId}/sub")
    public ApiResult<?> registerSub(@PathVariable Integer mainId,
        @RequestBody SubQuestionCommandRequestDto dto) {
        SubQuestionCommandResponseDto subQuestionCommandResponseDto = subCommandService.registerSubQuestion(
            mainId, dto);
        return success(subQuestionCommandResponseDto);
//        return ResponseEntity.ok(subCommandService.registerSubQuestion(mainId, dto));
    }

    @Operation(summary = "서브 질문 삭제")
    @DeleteMapping("/main/{mainId}/sub/{subId}")
    public ApiResult<?> deleteSub(@PathVariable Integer subId) {
        boolean result = subCommandService.deleteSubQuestion(subId);
        return success(result);
//        return subCommandService.deleteSubQuestion(subId)
//            ? ResponseEntity.ok().build() : ResponseEntity.badRequest().build();
    }

    @Operation(summary = "서브 질문 수정")
    @PatchMapping("/main/{mainId}/sub/{subId}")
    public ApiResult<?> patchSub(@PathVariable Integer subId,
        @RequestBody SubQuestionCommandRequestDto dto) {
        SubQuestionCommandResponseDto subQuestionCommandResponseDto = subCommandService.modifySubQuestion(
            subId, dto);
        return success(subQuestionCommandResponseDto);
//        return ResponseEntity.ok(subCommandService.modifySubQuestion(subId, dto));
    }
}
