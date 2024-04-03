package com.ssafy.pickitup.domain.quiz.query.service;

import com.ssafy.pickitup.domain.quiz.dto.OxQuizResponseDto;
import com.ssafy.pickitup.domain.quiz.dto.SpeedQuizResponseDto;
import com.ssafy.pickitup.domain.quiz.entity.OxQuiz;
import com.ssafy.pickitup.domain.quiz.entity.SpeedQuiz;
import com.ssafy.pickitup.domain.quiz.query.repository.OxQuizJpaRepository;
import com.ssafy.pickitup.domain.quiz.query.repository.SpeedQuizJpaRepository;
import com.ssafy.pickitup.domain.user.command.repository.UserCommandJpaRepository;
import com.ssafy.pickitup.domain.user.entity.User;
import com.ssafy.pickitup.domain.user.exception.UserNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class QuizService {

    private final OxQuizJpaRepository oxQuizJpaRepository;
    private final SpeedQuizJpaRepository speedQuizJpaRepository;
    private final UserCommandJpaRepository userCommandJpaRepository;

    public List<OxQuizResponseDto> getOxQuiz(String category) {
        List<OxQuiz> oxQuizList = oxQuizJpaRepository.findAllByCategory(category);
        return oxQuizList.stream()
            .map(OxQuizResponseDto::toDto)
            .toList();
    }

    public List<SpeedQuizResponseDto> getSpeedQuiz(String category) {
        List<SpeedQuiz> speedQuizList = speedQuizJpaRepository.findAllByCategory(category);
        return speedQuizList.stream()
            .map(SpeedQuizResponseDto::toDto)
            .toList();
    }

    @Transactional
    public int increaseScore(Integer authId) {
        User user = userCommandJpaRepository.findById(authId)
            .orElseThrow(UserNotFoundException::new);
        return user.increaseWinCount();
    }

}
