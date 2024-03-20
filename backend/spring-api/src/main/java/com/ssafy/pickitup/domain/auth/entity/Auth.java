package com.ssafy.pickitup.domain.auth.entity;

import com.ssafy.pickitup.domain.auth.query.dto.AuthDto;
import com.ssafy.pickitup.domain.user.entity.BaseTimeEntity;
import com.ssafy.pickitup.domain.user.entity.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Getter
@Builder
@SQLRestriction("is_deleted = false")
@ToString(of = {"id", "username", "password", "refreshToken"})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Auth extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true)
    private String username;
    private String password;
    private String name;
    private String email;
    private String provider;
    private String providerId;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private Role role = Role.USER;

    private String refreshToken;
    private boolean isDeleted;

    @OneToOne(mappedBy = "auth", cascade = CascadeType.ALL)
    private User user;

    public static Auth toDto(AuthDto authDto) {
        return Auth.builder()
            .id(authDto.getId())
            .username(authDto.getUsername())
            .password(authDto.getPassword())
            .name(authDto.getName())
            .role(authDto.getRole())
            .email(authDto.getEmail())
            .provider(authDto.getProvider())
            .providerId(authDto.getProviderId())
            .refreshToken(authDto.getRefreshToken())
            .build();
    }

    public void deleteRefreshToken() {
        this.refreshToken = null;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public void deactivate() {
        this.isDeleted = true;
    }

    public void activate() {
        this.isDeleted = false;
    }

}
