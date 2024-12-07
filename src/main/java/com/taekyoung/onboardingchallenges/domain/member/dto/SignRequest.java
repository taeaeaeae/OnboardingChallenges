package com.taekyoung.onboardingchallenges.domain.member.dto;

public record SignRequest(
        String username,
        String password
) {
}