package com.taekyoung.onboardingchallenges;

import com.taekyoung.onboardingchallenges.domain.member.dto.AuthoritiesName;
import com.taekyoung.onboardingchallenges.infra.security.jwt.JwtHelper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TokenTest {

    @Autowired
    private JwtHelper jwtHelper;

    @Test
    public void generateAccessToken() {
        String token = jwtHelper.generateAccessToken(
                "accessToken test",
                AuthoritiesName.ROLE_USER
        );
        assert token != null;
    }

    @Test
    public void generateRefreshToken() {
        String token = jwtHelper.generateRefreshToken(
                "accessToken test"
        );
        assert token != null;
    }


}
