package com.ssafy.pickitup.domain.recruit.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
@Document(collection = "recruit")
public class RecruitingDocumentMongo {
    @Id
    private Integer id;

    private String source;
    private String title;
    private String company;
    private String url;
    private String thumbnailUrl;
    private Set<String> qualificationRequirements;
    private Set<String> preferredRequirements;
    private String dueDate;
    private String career;
    private String collectTime;
}