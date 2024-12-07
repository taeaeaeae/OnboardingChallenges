package com.taekyoung.onboardingchallenges.domain.member.model;

import com.taekyoung.onboardingchallenges.domain.member.dto.AuthoritiesName;
import com.taekyoung.onboardingchallenges.domain.member.dto.SignupResponse;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "member")
public class Member {
    public Member() {
    }

    public Member(String username, String nickname, String password) {
        this.username = username;
        this.nickname = nickname;
        this.password = password;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id = null;
    @Column(unique = true, nullable = false)
    private String username;
    private String nickname;
    private String password;

    @Enumerated(EnumType.STRING)
    private AuthoritiesName authorityName = AuthoritiesName.ROLE_USER;

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public AuthoritiesName getAuthorityName() {
        return authorityName;
    }

    public SignupResponse toResponse() {
        return new SignupResponse(
                username,
                nickname,
                List.of(authorityName)
        );
    }
}