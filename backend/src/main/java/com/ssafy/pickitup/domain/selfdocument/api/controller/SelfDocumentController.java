package com.ssafy.pickitup.domain.selfdocument.api.controller;

import com.ssafy.pickitup.domain.selfdocument.command.MainQuestionCommandService;
import com.ssafy.pickitup.domain.selfdocument.command.SubQuestionCommandService;
import com.ssafy.pickitup.domain.selfdocument.command.dto.MainQuestionCommandRequestDto;
import com.ssafy.pickitup.domain.selfdocument.command.dto.SubQuestionCommandRequestDto;
import com.ssafy.pickitup.domain.selfdocument.query.MainQuestionQueryService;
import com.ssafy.pickitup.domain.selfdocument.query.SubQuestionQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/self")
@RestController
@RequiredArgsConstructor
public class SelfDocumentController {

    private final MainQuestionQueryService mainQueryService;
    private final MainQuestionCommandService mainCommandService;
    private final SubQuestionQueryService subQueryService;
    private final SubQuestionCommandService subCommandService;

    @GetMapping("/main")
    public ResponseEntity<?> searchMain(@RequestParam("userId") Integer userId) {
        return ResponseEntity.ok(mainQueryService.searchMainQuestions(userId));
    }

    @PostMapping("/main")
    public ResponseEntity<?> registerMain(
        @RequestBody MainQuestionCommandRequestDto dto, @RequestParam("userId") Integer userId) {
        return ResponseEntity.ok(
            mainCommandService.registerMainQuestion(dto, userId));
    }

    @DeleteMapping("/main/{mainId}")
    public ResponseEntity<?> deleteMain(@PathVariable Integer mainId) {
        return mainCommandService.deleteMainQuestion(mainId)
            ? ResponseEntity.ok().build() : ResponseEntity.badRequest().build();
    }

    @PatchMapping("/main/{mainId}")
    public ResponseEntity<?> patchMain(@PathVariable Integer mainId,
        @RequestBody MainQuestionCommandRequestDto dto) {
        return ResponseEntity.ok(mainCommandService.modifyMainQuestion(mainId, dto));
    }

    @GetMapping("/main/{mainId}/sub")
    public ResponseEntity<?> searchSub(@PathVariable Integer mainId) {
        return ResponseEntity.ok(subQueryService.searchSubQuestions(mainId));
    }

    @PostMapping("/main/{mainId}/sub")
    public ResponseEntity<?> registerSub(@PathVariable Integer mainId,
        @RequestBody SubQuestionCommandRequestDto dto) {
        return ResponseEntity.ok(subCommandService.registerSubQuestion(mainId, dto));
    }

    @DeleteMapping("/main/{mainId}/sub/{subId}")
    public ResponseEntity<?> deleteSub(@PathVariable Integer subId) {
        return subCommandService.deleteSubQuestion(subId)
            ? ResponseEntity.ok().build() : ResponseEntity.badRequest().build();
    }

    @PatchMapping("/main/{mainId}/sub/{subId}")
    public ResponseEntity<?> patchSub(@PathVariable Integer subId,
        @RequestBody SubQuestionCommandRequestDto dto) {
        return ResponseEntity.ok(subCommandService.modifySubQuestion(subId, dto));
    }
}
