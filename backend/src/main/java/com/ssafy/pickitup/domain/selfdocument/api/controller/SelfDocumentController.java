package com.ssafy.pickitup.domain.selfdocument.api.controller;

import com.ssafy.pickitup.domain.selfdocument.command.MainQuestionCommandService;
import com.ssafy.pickitup.domain.selfdocument.command.dto.MainQuestionCommandRequestDto;
import com.ssafy.pickitup.domain.selfdocument.query.MainQuestionQueryService;
import com.ssafy.pickitup.domain.user.entity.User;
import com.ssafy.pickitup.domain.user.query.UserQueryJpaRepository;
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

    private final MainQuestionQueryService mainQuestionQueryService;
    private final MainQuestionCommandService mainQuestionCommandService;
    private final UserQueryJpaRepository userQueryJpaRepository;

    @GetMapping("/main")
    public ResponseEntity<?> searchMain(@RequestParam("userId") Integer userId) {
        return ResponseEntity.ok(mainQuestionQueryService.searchMainQuestions(userId));
    }

    @PostMapping("/main")
    public ResponseEntity<?> registerMain(
        @RequestBody MainQuestionCommandRequestDto dto, @RequestParam("userId") Integer userId) {
        User user = userQueryJpaRepository.findById(userId).get(); // 예외처리 필요
        return ResponseEntity.ok(
            mainQuestionCommandService.registerMainQuestion(dto, user));
    }

    @DeleteMapping("/main/{mainId}")
    public ResponseEntity<?> deleteMain(@PathVariable Integer mainId) {
        return mainQuestionCommandService.deleteMainQuestion(mainId)
            ? ResponseEntity.ok().build() : ResponseEntity.badRequest().build();
    }

    @PatchMapping("/main/{mainId}")
    public ResponseEntity<?> patchMain(@PathVariable Integer mainId,
        @RequestBody MainQuestionCommandRequestDto dto) {
        return ResponseEntity.ok(mainQuestionCommandService.updateMainQuestion(mainId, dto));
    }
}
