package com.ssafy.pickitup.domain.selfdocument.api.controller;

import com.ssafy.pickitup.domain.selfdocument.command.MainQuestionCommandService;
import com.ssafy.pickitup.domain.selfdocument.command.dto.MainQuestionCommandRequestDto;
import com.ssafy.pickitup.domain.selfdocument.query.MainQuestionQueryService;
import com.ssafy.pickitup.domain.user.entity.User;
import com.ssafy.pickitup.domain.user.query.UserQueryService;
import com.ssafy.pickitup.domain.user.query.dto.UserResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
    private final UserQueryService userQueryService;

    @GetMapping("/main")
    public ResponseEntity<?> searchMain(@RequestParam("userId") Integer userId) {
        return ResponseEntity.ok(mainQuestionQueryService.searchMainQuestions(userId));
    }

    @PostMapping("/main")
    public ResponseEntity<?> registerMain(
        @RequestBody MainQuestionCommandRequestDto dto, @RequestParam("userId") Integer userId) {
        UserResponseDto userResponseDto = userQueryService.getUserById(userId);
        User user = User.builder()
            .id(userResponseDto.getId())
            .name(userResponseDto.getName())
            .build();
        return ResponseEntity.ok(
            mainQuestionCommandService.registerMain(dto, user));
    }
}
