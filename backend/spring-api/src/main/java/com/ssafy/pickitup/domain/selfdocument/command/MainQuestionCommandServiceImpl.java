package com.ssafy.pickitup.domain.selfdocument.command;

import com.ssafy.pickitup.domain.selfdocument.command.dto.MainQuestionCommandRequestDto;
import com.ssafy.pickitup.domain.selfdocument.command.dto.MainQuestionCommandResponseDto;
import com.ssafy.pickitup.domain.selfdocument.entity.MainQuestion;
import com.ssafy.pickitup.domain.selfdocument.exception.MainQuestionNotFoundException;
import com.ssafy.pickitup.domain.selfdocument.query.MainQuestionQueryJpaRepository;
import com.ssafy.pickitup.domain.user.entity.User;
import com.ssafy.pickitup.domain.user.exception.UserNotFoundException;
import com.ssafy.pickitup.domain.user.query.UserQueryJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class MainQuestionCommandServiceImpl implements MainQuestionCommandService {

    private final MainQuestionCommandJpaRepository mainCommandRepository;
    private final MainQuestionQueryJpaRepository mainQueryRepository;
    private final UserQueryJpaRepository userQueryRepository;

    @Override
    @Transactional
    public MainQuestionCommandResponseDto registerMainQuestion(Integer userId,
        MainQuestionCommandRequestDto dto) {
        User user = userQueryRepository.findById(userId)
            .orElseThrow(() -> new UserNotFoundException("유저를 찾을 수 없습니다"));
        MainQuestion mainQuestion = dto.toEntity(user);
        return mainCommandRepository.save(mainQuestion).toCommandResponse();
    }

    @Override
    @Transactional
    public boolean deleteMainQuestion(Integer mainId) {
        try {
            MainQuestion mainQuestion = mainQueryRepository.findById(mainId)
                .orElseThrow(MainQuestionNotFoundException::new);

            mainCommandRepository.delete(mainQuestion);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    @Transactional
    public MainQuestionCommandResponseDto modifyMainQuestion(Integer id,
        MainQuestionCommandRequestDto dto) {
        MainQuestion mainQuestion = mainQueryRepository.findById(id)
            .orElseThrow(MainQuestionNotFoundException::new);
        mainQuestion.setTitle(dto.getTitle());
        return mainCommandRepository.save(mainQuestion).toCommandResponse();
    }
}
