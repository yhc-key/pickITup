package com.ssafy.pickitup.domain.quiz.dto;

import com.ssafy.pickitup.domain.quiz.entity.SpeedQuiz;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SpeedQuizResponseDto {

    private String question;
    private String answer;

    public static SpeedQuizResponseDto toDto(SpeedQuiz speedQuiz) {
        return SpeedQuizResponseDto.builder()
            .question(speedQuiz.getQuestion())
            .answer(speedQuiz.getAnswer())
            .build();
    }
}
