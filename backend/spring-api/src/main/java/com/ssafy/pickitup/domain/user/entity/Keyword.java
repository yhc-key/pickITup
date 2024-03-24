package com.ssafy.pickitup.domain.user.entity;


import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of = {"id", "name"})
@AllArgsConstructor
public class Keyword {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;

    @OneToMany(mappedBy = "keyword")
    private List<UserKeyword> userKeywords = new ArrayList<>();

    public Keyword(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

}
