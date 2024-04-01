package com.ssafy.pickitup.domain.user.entity;

import com.ssafy.pickitup.domain.auth.entity.Auth;
import com.ssafy.pickitup.domain.badge.entity.UserBadge;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@ToString(of = {"id", "nickname", "level", "userKeywords", "address", "userRank"})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue
    private Integer id;

    @Builder.Default
    @Column(columnDefinition = "INT DEFAULT 1")
    private Integer profile = 1;
    private String nickname;
    private String github;
    private String techBlog;

    @Builder.Default
    @Column(columnDefinition = "INT DEFAULT 0")
    private Integer recruitScrapCount = 0;
    @Builder.Default
    @Column(columnDefinition = "INT DEFAULT 0")
    private Integer recruitViewCount = 0;
    @Builder.Default
    @Column(columnDefinition = "INT DEFAULT 0")
    private Integer attendCount = 0;
    @Builder.Default
    @Column(columnDefinition = "INT DEFAULT 0")
    private Integer selfAnswerCount = 0;
    @Builder.Default
    @Column(columnDefinition = "INT DEFAULT 0")
    private Integer gameWinCount = 0;
    @Builder.Default
    @Column(columnDefinition = "INT DEFAULT 1")
    private Integer level = 0;
    @Builder.Default
    private Integer exp = 0;
    @Builder.Default
    @Column(columnDefinition = "VARCHAR(255) DEFAULT ''")
    private String address = "";

    @Builder.Default
    @Enumerated(EnumType.STRING)
    private Rank userRank = Rank.NORMAL;

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

    public int expCalculator() {

        return (3 * this.recruitViewCount) + (5 * this.recruitScrapCount) + (2 * this.attendCount)
            + (6
            * this.selfAnswerCount) + (4 * this.gameWinCount);
    }

    public void changeNickname(String nickname) {
        this.nickname = nickname;
    }

    public void changeProfile(Integer profile) {
        this.profile = profile;
    }

    public void changeAddress(String address) {
        this.address = address;
    }

    public void changeGithub(String github) {
        this.github = github;
    }

    public void changeTechBlog(String techBlog) {
        this.techBlog = techBlog;
    }

    public int increaseWinCount() {
        return ++gameWinCount;
    }

    public void setUserKeywords(List<UserKeyword> userKeywords) {
        this.userKeywords = userKeywords;
    }

    public void serUserLevel(int level) {
        this.level = level;
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

    public boolean checkMyRank() {
        return (0.3 * (this.recruitViewCount) + (0.7 * this.recruitScrapCount) > 50);
    }

    public void upgradeToSuper() {
        this.userRank = Rank.SUPER;
    }

}
