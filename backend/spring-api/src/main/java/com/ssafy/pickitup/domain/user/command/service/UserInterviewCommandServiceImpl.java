package com.ssafy.pickitup.domain.user.command.service;

import com.ssafy.pickitup.domain.interview.entity.Interview;
import com.ssafy.pickitup.domain.interview.exception.InterviewNotFoundException;
import com.ssafy.pickitup.domain.interview.repository.InterviewRepository;
import com.ssafy.pickitup.domain.user.entity.User;
import com.ssafy.pickitup.domain.user.entity.UserInterview;
import com.ssafy.pickitup.domain.user.exception.UserNotFoundException;
import com.ssafy.pickitup.domain.user.query.UserQueryJpaRepository;
import com.ssafy.pickitup.domain.user.repository.UserInterviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserInterviewCommandServiceImpl implements UserInterviewCommandService {

    private final InterviewRepository interviewRepository;
    private final UserQueryJpaRepository userQueryJpaRepository;
    private final UserInterviewRepository userInterviewRepository;

    @Override
    @Transactional
    public void createOrUpdateUserInterview(Integer userId, Integer interviewId, String answer) {

        User user = userQueryJpaRepository.findById(userId)
            .orElseThrow(UserNotFoundException::new);
        user.increaseSelfAnswerCount();

        userInterviewRepository.findByUserIdAndInterviewId(userId, interviewId)
            .ifPresentOrElse(userInterview -> userInterview.updateAnswer(answer), () -> {
                Interview interview = interviewRepository.findById(interviewId)
                    .orElseThrow(InterviewNotFoundException::new);

                UserInterview userInterview = UserInterview.builder()
                    .user(user)
                    .interview(interview)
                    .answer(answer)
                    .build();
                userInterviewRepository.save(userInterview);
            });
    }
}
