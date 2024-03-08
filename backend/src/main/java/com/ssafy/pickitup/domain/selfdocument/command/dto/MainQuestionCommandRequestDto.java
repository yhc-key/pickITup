package com.ssafy.pickitup.domain.selfdocument.command.dto;

import com.ssafy.pickitup.domain.selfdocument.entity.MainQuestion;
import com.ssafy.pickitup.domain.user.entity.User;
import java.util.ArrayList;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@Builder
public class MainQuestionCommandRequestDto {

    private String title;

    public MainQuestion toEntity(User user) {
        return MainQuestion.builder()
            .title(this.title)
            .subQuestions(new ArrayList<>())
            .user(user)
            .build();
    }
}
