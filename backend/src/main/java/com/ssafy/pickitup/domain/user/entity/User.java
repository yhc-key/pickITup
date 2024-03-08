package com.ssafy.pickitup.domain.user.entity;

import com.fasterxml.jackson.databind.ser.Serializers.Base;
import com.ssafy.pickitup.domain.auth.entity.Auth;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter
@Builder
@ToString(of = {"id"})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nickname;
    private String profile;
    private String email;
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

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "auth_id")
    private Auth auth;

}
