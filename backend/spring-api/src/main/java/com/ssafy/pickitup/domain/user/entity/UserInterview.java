package com.ssafy.pickitup.domain.user.entity;

import com.ssafy.pickitup.domain.interview.entity.Interview;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

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

  @OneToOne
  @JoinColumn(name = "interivew_id", referencedColumnName = "id")
  private Interview interview;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", referencedColumnName = "id")
  private User user;

  public void updateAnswer(String answer) {
    this.answer = answer;
  }
}
