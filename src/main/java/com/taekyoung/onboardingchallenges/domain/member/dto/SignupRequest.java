package com.taekyoung.onboardingchallenges.domain.member.dto;

public record SignupRequest (
        String username,
        String password,
        String nickname
) {}
