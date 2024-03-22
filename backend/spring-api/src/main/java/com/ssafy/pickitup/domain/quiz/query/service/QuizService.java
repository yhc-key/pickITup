package com.ssafy.pickitup.domain.quiz.query.service;

import com.ssafy.pickitup.domain.quiz.dto.OxQuizResponseDto;
import com.ssafy.pickitup.domain.quiz.dto.SpeedQuizResponseDto;
import com.ssafy.pickitup.domain.quiz.entity.OxQuiz;
import com.ssafy.pickitup.domain.quiz.entity.SpeedQuiz;
import com.ssafy.pickitup.domain.quiz.query.repository.OxQuizJpaRepository;
import com.ssafy.pickitup.domain.quiz.query.repository.SpeedQuizJpaRepository;
import com.ssafy.pickitup.domain.user.command.UserCommandJpaRepository;
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

    @Transactional
    public int increaseScore(Integer authId) {
        User user = userCommandJpaRepository.findByAuthId(authId);
        if (user == null) {
            throw new UserNotFoundException("유저를 찾을 수 없습니다");
        }
        return user.increaseWinCount();
    }

}
