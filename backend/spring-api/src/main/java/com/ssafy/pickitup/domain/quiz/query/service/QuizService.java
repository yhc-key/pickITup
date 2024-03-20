package com.ssafy.pickitup.domain.quiz.query.service;

import com.ssafy.pickitup.domain.quiz.dto.OxQuizResponseDto;
import com.ssafy.pickitup.domain.quiz.dto.SpeedQuizResponseDto;
import com.ssafy.pickitup.domain.quiz.entity.OxQuiz;
import com.ssafy.pickitup.domain.quiz.entity.SpeedQuiz;
import com.ssafy.pickitup.domain.quiz.query.repository.OxQuizJpaRepository;
import com.ssafy.pickitup.domain.quiz.query.repository.SpeedQuizJpaRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QuizService {

    private final OxQuizJpaRepository oxQuizJpaRepository;
    private final SpeedQuizJpaRepository speedQuizJpaRepository;

    public List<OxQuizResponseDto> getOxQuiz(String category) {
        List<OxQuiz> oxQuizList = oxQuizJpaRepository.findAllByCategory(category);
        List<OxQuizResponseDto> oxQuizResponseDtoList = oxQuizList.stream()
            .map(oxQuiz -> OxQuizResponseDto.toDto(oxQuiz))
            .toList();
        return oxQuizResponseDtoList;
    }

    public List<SpeedQuizResponseDto> getSpeedQuiz(String category) {
        List<SpeedQuiz> speedQuizList = speedQuizJpaRepository.findAllByCategory(category);
        List<SpeedQuizResponseDto> speedQuizResponseDtoList = speedQuizList.stream()
            .map(speedQuiz -> SpeedQuizResponseDto.toDto(speedQuiz))
            .toList();
        return speedQuizResponseDtoList;
    }

}
