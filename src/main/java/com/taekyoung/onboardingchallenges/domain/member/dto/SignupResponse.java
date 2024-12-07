package com.taekyoung.onboardingchallenges.domain.member.dto;

import java.util.List;

public record SignupResponse(
        String username,
        String nickname,
        List<AuthoritiesName> authorities
) {
}
