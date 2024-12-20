package com.taekyoung.onboardingchallenges.infra.security;

import com.taekyoung.onboardingchallenges.domain.member.dto.AuthoritiesName;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.Collections;

public record MemberPrincipal(
        String id,
        Collection<GrantedAuthority> authorities
) {
    public MemberPrincipal(String id, AuthoritiesName role) {
        this(id, Collections.singleton(new SimpleGrantedAuthority("ROLE_" + role.name())));
    }
}