package com.ssafy.pickitup.domain.selfdocument.entity;

import com.ssafy.pickitup.domain.selfdocument.command.dto.MainQuestionCommandResponseDto;
import com.ssafy.pickitup.domain.selfdocument.query.dto.MainQuestionQueryResponseDto;
import com.ssafy.pickitup.domain.user.entity.User;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MainQuestion extends BaseEntity {

    private String title;

    @OneToMany
    private List<SubQuestion> subQuestions;

    @ManyToOne
    private User user;

    public MainQuestionQueryResponseDto toQueryResponse() {
        return MainQuestionQueryResponseDto.builder()
            .id(this.getId())
            .title(this.title)
            .build();
    }

    public MainQuestionCommandResponseDto toCommandResponse() {
        return MainQuestionCommandResponseDto.builder()
            .id(this.getId())
            .title(this.title)
            .build();
    }
}
