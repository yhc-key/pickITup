package com.ssafy.pickitup.domain.selfdocument.entity;

import com.ssafy.pickitup.domain.selfdocument.command.dto.SubQuestionCommandResponseDto;
import com.ssafy.pickitup.domain.selfdocument.query.dto.SubQuestionQueryResponseDto;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
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
public class SubQuestion extends BaseEntity {

    private String title;
    private String content;
    private String company;

    @ManyToOne
    private MainQuestion mainQuestion;

    public SubQuestionQueryResponseDto toQueryResponse() {
        return SubQuestionQueryResponseDto.builder()
            .id(this.getId())
            .title(this.title)
            .content(this.content)
            .company(this.company)
            .build();
    }

    public SubQuestionCommandResponseDto toCommandResponse() {
        return SubQuestionCommandResponseDto.builder()
            .id(this.getId())
            .title(this.title)
            .content(this.content)
            .company(this.company)
            .build();
    }
}
