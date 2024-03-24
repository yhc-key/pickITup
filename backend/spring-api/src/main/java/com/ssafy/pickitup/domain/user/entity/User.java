package com.ssafy.pickitup.domain.user.entity;

import com.ssafy.pickitup.domain.auth.entity.Auth;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

@Entity
@Getter
@Builder
@ToString(of = {"id", "nickname"})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class User extends BaseTimeEntity {

    //    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @GeneratedValue
    private Integer id;

    private String nickname;
    private String profile;
    private String github;
    private String techBlog;

    @Builder.Default
    private Integer recruitViewCount = 0;
    @Builder.Default
    private Integer recruitScrapCount = 0;
    @Builder.Default
    private Integer blogViewCount = 0;
    @Builder.Default
    private Integer blogScrapCount = 0;
    @Builder.Default
    private Integer attendCount = 0;
    @Builder.Default
    private Integer selfAnswerCount = 0;
    @Builder.Default
    private Integer gameWinCount = 0;
    @Builder.Default
    private Integer level = 0;
    @Builder.Default
    private Integer exp = 0;

    @MapsId
    @OneToOne
    @JoinColumn(name = "id", referencedColumnName = "id")
//    @JoinColumn
    private Auth auth;

    @OneToMany(mappedBy = "user")
    private List<UserKeyword> userKeywords = new ArrayList<>();


    public void changeNickname(String nickname) {
        this.nickname = nickname;
    }

    public int increaseWinCount() {
        return ++gameWinCount;
    }



}
