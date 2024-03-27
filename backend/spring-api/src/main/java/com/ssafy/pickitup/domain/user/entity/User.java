package com.ssafy.pickitup.domain.user.entity;

import com.ssafy.pickitup.domain.auth.entity.Auth;
import com.ssafy.pickitup.domain.badge.entity.UserBadge;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Entity
@Getter
@Builder
@ToString(of = {"id", "nickname"})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class User extends BaseTimeEntity {

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
    private Integer attendCount = 0;
    @Builder.Default
    private Integer selfAnswerCount = 0;
    @Builder.Default
    private Integer gameWinCount = 0;
    @Builder.Default
    private Integer level = 0;
    @Builder.Default
    private Integer exp = 0;
    private String address;

    @MapsId
    @OneToOne
    @JoinColumn(name = "id", referencedColumnName = "id")
    private Auth auth;

    @Setter
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<UserKeyword> userKeywords = new ArrayList<>();

    @Setter
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<UserBadge> userBadges = new ArrayList<>();

    public void changeNickname(String nickname) {
        this.nickname = nickname;
    }

    public int increaseWinCount() {
        return ++gameWinCount;
    }

    public void setUserKeywords(List<UserKeyword> userKeywords) {
        this.userKeywords = userKeywords;
    }

    public int increaseAttendCount() {
        return ++this.attendCount;
    }

    public int increaseRecruitViewCount() {
        return ++this.recruitViewCount;
    }

    public int increaseRecruitScrapCount() {
        return ++this.recruitScrapCount;
    }

    public int increaseSelfAnswerCount() {
        return ++this.selfAnswerCount;
    }

}
