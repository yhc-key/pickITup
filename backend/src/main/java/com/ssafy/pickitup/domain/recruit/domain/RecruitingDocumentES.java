package com.ssafy.pickitup.domain.recruit.domain;

import java.util.HashSet;
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
public class RecruitingDocumentES {

  @Id
  private Integer id;

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

  public RecruitingDocumentMongo toMongo() {
    return RecruitingDocumentMongo.builder()
        .id(this.id)
        .source(this.source)
        .company(this.company)
        .url(this.url)
        .thumbnailUrl(this.thumbnailUrl)
        .qualificationRequirements(new HashSet<>())
        .preferredRequirements(new HashSet<>())
        .dueDate(this.dueDate)
        .career(this.career)
        .collectTime(this.collectTime)
        .build();
  }
}
