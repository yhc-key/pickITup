package com.ssafy.pickitup.domain.interview.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Entity
@Getter
@Table(name = "interview")
public class Interview {

  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column @NotNull
  private String question;

  @Column @NotNull
  private String example;

  @Column @NotNull
  private String mainCategory;

  @Column @NotNull
  private String subCategory;
}
