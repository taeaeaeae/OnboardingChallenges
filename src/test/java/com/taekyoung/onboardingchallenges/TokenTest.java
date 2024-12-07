package com.taekyoung.onboardingchallenges;

import com.taekyoung.onboardingchallenges.domain.member.dto.AuthoritiesName;
import com.taekyoung.onboardingchallenges.infra.security.jwt.JwtHelper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.test.util.ReflectionTestUtils;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TokenTest {

    private JwtHelper jwtHelper;


    @BeforeEach
    void setUp() {
        jwtHelper = new JwtHelper();
        ReflectionTestUtils.setField(jwtHelper, "secretKey", "asdfasdfasdfag4rwegregrfedgfdgfdrsagwefafewwfe");
        ReflectionTestUtils.setField(jwtHelper, "issuer", "testIssuer");
        ReflectionTestUtils.setField(jwtHelper, "accessTokenExpirationHour", 1);
        ReflectionTestUtils.setField(jwtHelper, "refreshTokenExpirationHour", 24);
    }

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
                "refreshToken test"
        );
        assert token != null;
    }

    @Test
    public void validateToken() {
        String token = jwtHelper.generateAccessToken(
                "accessToken test",
                AuthoritiesName.ROLE_USER
        );

        Jws<Claims> claim = jwtHelper.validateTokenAndGetClaims(token);
        assert claim != null;
    }


}
