package com.taekyoung.onboardingchallenges.infra.security.jwt;

import com.taekyoung.onboardingchallenges.domain.member.dto.AuthoritiesName;
import com.taekyoung.onboardingchallenges.infra.security.MemberPrincipal;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtHelper jwtHelper;

    public JwtAuthenticationFilter(JwtHelper jwtHelper) {
        this.jwtHelper = jwtHelper;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        String token = getBearerToken(request);

        if (token != null) {
            var claimsJws = jwtHelper.validateTokenAndGetClaims(token);
            var claims = claimsJws.getBody();

            long id = Long.parseLong(claims.getSubject());
            String roleName = (String) claims.get("role");
            AuthoritiesName role = AuthoritiesName.valueOf(roleName);

            WebAuthenticationDetailsSource detailsSource = new WebAuthenticationDetailsSource();
            JwtAuthenticationToken authentication = new JwtAuthenticationToken(
                    new MemberPrincipal(id, role),
                    null,
                    detailsSource.buildDetails(request)
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);

        }

        filterChain.doFilter(request, response);
    }

    private String getBearerToken(HttpServletRequest request) {
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (header == null) return null;
        String prefix = "Bearer ";
        return header.toLowerCase().startsWith(prefix.toLowerCase()) ? header.substring(prefix.length()) : null;
    }
}