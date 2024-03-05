package com.ssafy.pickitup.domain.recruit.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName = "searchrecruit")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RecruitingDocument {
    @Id
    private String id;

    private String source;
    private String title;
    private String company;
    private String url;
    private String thumbnailUrl;
    private String qualificationRequirements;
    private String preferredRequirements;
    private String dueDate;
    private String career;
    private String collectTime;
}
