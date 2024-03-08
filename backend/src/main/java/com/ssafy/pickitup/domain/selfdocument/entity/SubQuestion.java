package com.ssafy.pickitup.domain.selfdocument.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Builder
public class SubQuestion extends BaseEntity {

    private String title;
    private String content;
    private String company;

    @ManyToOne
    private MainQuestion mainQuestion;
}
