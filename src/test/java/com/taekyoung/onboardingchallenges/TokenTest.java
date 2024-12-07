package com.taekyoung.onboardingchallenges;

import com.taekyoung.onboardingchallenges.domain.member.dto.AuthoritiesName;
import com.taekyoung.onboardingchallenges.infra.security.jwt.JwtHelper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.test.util.ReflectionTestUtils;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertThrows;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TokenTest {

    private JwtHelper jwtHelper;


    @BeforeAll
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
        assertThat(token, notNullValue());
    }

    @Test
    public void generateRefreshToken() {
        String token = jwtHelper.generateRefreshToken(
                "refreshToken test"
        );
        assertThat(token, notNullValue());
    }

    @Test
    public void validateToken() {
        String token = jwtHelper.generateAccessToken(
                "accessToken test",
                AuthoritiesName.ROLE_USER
        );

        Jws<Claims> claim = jwtHelper.validateTokenAndGetClaims(token);

        assertThat(claim, notNullValue());
    }

    @Test
    public void ExpiredToken() {
        ReflectionTestUtils.setField(jwtHelper, "accessTokenExpirationHour", -1);
        String token = jwtHelper.generateAccessToken(
                "token expired test",
                AuthoritiesName.ROLE_USER
        );

        assertThrows(ExpiredJwtException.class, () -> jwtHelper.validateTokenAndGetClaims(token));
    }



}
