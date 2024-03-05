package com.ssafy.pickitup.domain.recruit.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;

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
    @Field(name = "thumbnail_url")
    private String thumbnailUrl;
    @Field(name = "qualification_requirements")
    private String qualificationRequirements;
    @Field(name = "preferred_requirements")
    private String preferredRequirements;
    @Field(name = "due_date")
    private String dueDate;
    private String career;
    @Field(name = "collect_time")
    private String collectTime;
}
