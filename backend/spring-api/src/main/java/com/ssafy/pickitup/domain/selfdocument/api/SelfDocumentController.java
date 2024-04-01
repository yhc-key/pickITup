package com.ssafy.pickitup.domain.selfdocument.api;

import static com.ssafy.pickitup.global.api.ApiUtils.success;

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
import com.ssafy.pickitup.global.annotation.AuthID;
import com.ssafy.pickitup.global.api.ApiUtils.ApiResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = {"https://pickitup.online", "http://localhost:3000",
    "http://localhost:8080", "https://spring.pickitup.online"}, exposedHeaders = "*")
@RestController
@RequiredArgsConstructor
@Tag(name = "SelfDocumentController", description = "자기소개서 관련 API")
@RequestMapping("/self")
public class SelfDocumentController {

    private final MainQuestionQueryService mainQueryService;
    private final MainQuestionCommandService mainCommandService;
    private final SubQuestionQueryService subQueryService;
    private final SubQuestionCommandService subCommandService;

    @Operation(summary = "메인 질문 조회")
    @GetMapping("/main")
    public ApiResult<List<MainQuestionQueryResponseDto>> searchMain(@AuthID Integer userId) {
        List<MainQuestionQueryResponseDto> mainQuestionQueryResponseDtoList
            = mainQueryService.searchMainQuestions(userId);
        return success(mainQuestionQueryResponseDtoList);
    }

    @Operation(summary = "메인 질문 등록")
    @PostMapping("/main")
    public ApiResult<MainQuestionCommandResponseDto> registerMain(
        @RequestBody MainQuestionCommandRequestDto dto, @AuthID Integer userId) {
        MainQuestionCommandResponseDto registeredMainQuestionDto =
            mainCommandService.registerMainQuestion(userId, dto);
        return success(registeredMainQuestionDto);
    }

    @Operation(summary = "메인 질문 삭제")
    @DeleteMapping("/main/{mainId}")
    public ApiResult<?> deleteMain(@PathVariable Integer mainId, @AuthID Integer userId) {
        boolean result = mainCommandService.deleteMainQuestion(mainId);
        return success(result);
    }

    @Operation(summary = "메인 질문 수정")
    @PatchMapping("/main/{mainId}")
    public ApiResult<?> patchMain(@PathVariable Integer mainId,
        @RequestBody MainQuestionCommandRequestDto dto, @AuthID Integer userId) {
        MainQuestionCommandResponseDto mainQuestionCommandResponseDto =
            mainCommandService.modifyMainQuestion(mainId, dto);
        return success(mainQuestionCommandResponseDto);
    }

    @Operation(summary = "서브 질문 조회")
    @GetMapping("/main/{mainId}/sub")
    public ApiResult<?> searchSub(@PathVariable Integer mainId, @AuthID Integer userId) {
        List<SubQuestionQueryResponseDto> subQuestionQueryResponseDtoList =
            subQueryService.searchSubQuestions(mainId);
        return success(subQuestionQueryResponseDtoList);
    }

    @Operation(summary = "서브 질문 등록")
    @PostMapping("/main/{mainId}/sub")
    public ApiResult<?> registerSub(@PathVariable Integer mainId,
        @RequestBody SubQuestionCommandRequestDto dto, @AuthID Integer userId) {
        SubQuestionCommandResponseDto subQuestionCommandResponseDto =
            subCommandService.registerSubQuestion(mainId, dto);
        return success(subQuestionCommandResponseDto);
    }

    @Operation(summary = "서브 질문 삭제")
    @DeleteMapping("/main/{mainId}/sub/{subId}")
    public ApiResult<?> deleteSub(@PathVariable Integer subId, @AuthID Integer userId) {
        boolean result = subCommandService.deleteSubQuestion(subId);
        return success(result);
    }

    @Operation(summary = "서브 질문 수정")
    @PatchMapping("/main/{mainId}/sub/{subId}")
    public ApiResult<?> patchSub(@PathVariable Integer subId,
        @RequestBody SubQuestionCommandRequestDto dto, @AuthID Integer userId) {
        SubQuestionCommandResponseDto subQuestionCommandResponseDto =
            subCommandService.modifySubQuestion(subId, dto);
        return success(subQuestionCommandResponseDto);
    }
}
