package com.ssafy.pickitup.domain.user.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class UserLevel {

    @Id
    @Column(name = "level", columnDefinition = "int DEFAULT 1")
    private int level;
    @Transient
    private int prevExp;
    private int exp;
    @Transient
    private int nextExp;

}
