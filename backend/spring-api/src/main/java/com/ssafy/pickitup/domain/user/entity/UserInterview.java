package com.ssafy.pickitup.domain.user.entity;

import com.ssafy.pickitup.domain.interview.entity.Interview;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_interview")
public class UserInterview extends BaseTimeEntity {

  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column @NotNull
  private String answer;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "interview_id", referencedColumnName = "id")
  private Interview interview;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", referencedColumnName = "id")
  private User user;

  public void updateAnswer(String answer) {
    this.answer = answer;
  }
}
