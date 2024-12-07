package com.taekyoung.onboardingchallenges.infra.security.jwt;

import com.taekyoung.onboardingchallenges.infra.security.MemberPrincipal;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

public class JwtAuthenticationToken extends AbstractAuthenticationToken {
    private final MemberPrincipal principal;

    public JwtAuthenticationToken(MemberPrincipal principal, WebAuthenticationDetails details, WebAuthenticationDetails webAuthenticationDetails) {
        super(principal.authorities());
        this.principal = principal;
        super.setAuthenticated(true);
        super.setDetails(details);
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return principal;
    }
}