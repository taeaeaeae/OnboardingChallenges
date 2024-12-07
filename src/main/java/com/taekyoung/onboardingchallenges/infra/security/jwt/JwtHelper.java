package com.taekyoung.onboardingchallenges.infra.security.jwt;

import com.taekyoung.onboardingchallenges.domain.member.dto.AuthoritiesName;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;

@Component
public class JwtHelper {
    @Value("${auth.jwt.issuer}")
    private String issuer;
    @Value("${auth.jwt.secretKey}")
    private String secretKey;
    @Value("${auth.jwt.accessTokenExpirationHour}")
    private long accessTokenExpirationHour;
    @Value("${auth.jwt.refreshTokenExpirationHour}")
    private long refreshTokenExpirationHour;

    public String generateAccessToken(Long memberId, AuthoritiesName role) {
        Instant now = Instant.now();
        Claims claims = Jwts.claims().build();
        claims.put("role", role);

        return Jwts.builder()
                .claims(claims)
                .subject(memberId.toString())
                .issuer(issuer)
                .issuedAt(Date.from(now))
                .expiration(Date.from(now.plusSeconds(accessTokenExpirationHour * 3600)))
                .signWith(Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8)))
                .compact();
    }

    public String generateRefreshToken(Long memberId) {
        Instant now = Instant.now();
        Claims claims = Jwts.claims().build();

        return Jwts.builder()
                .claims(claims)
                .issuer(issuer)
                .subject(memberId.toString())
                .issuedAt(Date.from(now))
                .expiration(Date.from(now.plusSeconds(refreshTokenExpirationHour * 3600)))
                .signWith(Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8)))
                .compact();
    }

    public Jws<Claims> validateTokenAndGetClaims(String token) {
        return Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8)))
                .build()
                .parseSignedClaims(token);
    }
}
