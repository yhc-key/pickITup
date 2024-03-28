package com.ssafy.pickitup.domain.quiz.dto;

import com.ssafy.pickitup.domain.quiz.entity.OxQuiz;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OxQuizResponseDto {

    private String question;
    private boolean answer;

    public static OxQuizResponseDto toDto(OxQuiz oxQuiz) {
        return OxQuizResponseDto.builder()
            .question(oxQuiz.getQuestion())
            .answer(oxQuiz.isAnswer())
            .build();
    }
}
