package com.ssafy.pickitup.domain.selfdocument.api.controller;

import com.ssafy.pickitup.domain.selfdocument.command.MainQuestionCommandService;
import com.ssafy.pickitup.domain.selfdocument.command.SubQuestionCommandService;
import com.ssafy.pickitup.domain.selfdocument.command.dto.MainQuestionCommandRequestDto;
import com.ssafy.pickitup.domain.selfdocument.command.dto.SubQuestionCommandRequestDto;
import com.ssafy.pickitup.domain.selfdocument.query.MainQuestionQueryService;
import com.ssafy.pickitup.domain.selfdocument.query.SubQuestionQueryService;
import com.ssafy.pickitup.security.jwt.JwtTokenProvider;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<?> searchMain(
        @RequestHeader(HttpHeaders.AUTHORIZATION) String accessToken) {
        int authId = Integer.parseInt(jwtTokenProvider.extractAuthId(accessToken));
        return ResponseEntity.ok(mainQueryService.searchMainQuestions(authId));
    }

    @Operation(summary = "메인 질문 등록")
    @PostMapping("/main")
    public ResponseEntity<?> registerMain(
        @RequestBody MainQuestionCommandRequestDto dto,
        @RequestHeader(HttpHeaders.AUTHORIZATION) String accessToken) {
        int authId = Integer.parseInt(jwtTokenProvider.extractAuthId(accessToken));
        return ResponseEntity.ok(
            mainCommandService.registerMainQuestion(authId, dto));
    }


    @Operation(summary = "메인 질문 삭제")
    @DeleteMapping("/main/{mainId}")
    public ResponseEntity<?> deleteMain(@PathVariable Integer mainId) {
        return mainCommandService.deleteMainQuestion(mainId)
            ? ResponseEntity.ok().build() : ResponseEntity.badRequest().build();
    }


    @Operation(summary = "메인 질문 수정")
    @PatchMapping("/main/{mainId}")
    public ResponseEntity<?> patchMain(@PathVariable Integer mainId,
        @RequestBody MainQuestionCommandRequestDto dto) {
        return ResponseEntity.ok(mainCommandService.modifyMainQuestion(mainId, dto));
    }


    @Operation(summary = "서브 질문 조회")
    @GetMapping("/main/{mainId}/sub")
    public ResponseEntity<?> searchSub(@PathVariable Integer mainId) {
        return ResponseEntity.ok(subQueryService.searchSubQuestions(mainId));
    }

    @Operation(summary = "서브 질문 등록")
    @PostMapping("/main/{mainId}/sub")
    public ResponseEntity<?> registerSub(@PathVariable Integer mainId,
        @RequestBody SubQuestionCommandRequestDto dto) {
        return ResponseEntity.ok(subCommandService.registerSubQuestion(mainId, dto));
    }

    @Operation(summary = "서브 질문 삭제")
    @DeleteMapping("/main/{mainId}/sub/{subId}")
    public ResponseEntity<?> deleteSub(@PathVariable Integer subId) {
        return subCommandService.deleteSubQuestion(subId)
            ? ResponseEntity.ok().build() : ResponseEntity.badRequest().build();
    }

    @Operation(summary = "서브 질문 수정")
    @PatchMapping("/main/{mainId}/sub/{subId}")
    public ResponseEntity<?> patchSub(@PathVariable Integer subId,
        @RequestBody SubQuestionCommandRequestDto dto) {
        return ResponseEntity.ok(subCommandService.modifySubQuestion(subId, dto));
    }
}
