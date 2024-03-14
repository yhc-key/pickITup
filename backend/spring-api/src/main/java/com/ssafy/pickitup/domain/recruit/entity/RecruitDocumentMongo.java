package com.ssafy.pickitup.domain.recruit.entity;

import com.ssafy.pickitup.domain.recruit.query.dto.RecruitQueryResponseDto;
import java.time.LocalDate;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
@Document(collection = "recruit")
public class RecruitDocumentMongo {

    @Id
    private Integer id;

    private String source;
    private String title;
    private String company;
    private String url;
    private String thumbnailUrl;
    private Set<String> qualificationRequirements;
    private Set<String> preferredRequirements;
    private LocalDate dueDate;
    private int[] career;
    private LocalDate collectTime;

    public RecruitQueryResponseDto toQueryResponse() {
        return RecruitQueryResponseDto.builder()
            .id(this.id)
            .source(this.source)
            .title(this.title)
            .company(this.company)
            .url(this.url)
            .thumbnailUrl(this.thumbnailUrl)
            .qualificationRequirements(this.qualificationRequirements)
            .preferredRequirements(this.preferredRequirements)
            .dueDate(this.dueDate)
            .career(this.career)
            .build();
    }
}