package com.taekyoung.onboardingchallenges.domain.member.service;

import com.taekyoung.onboardingchallenges.domain.member.dto.SignRequest;
import com.taekyoung.onboardingchallenges.domain.member.dto.SignResponse;
import com.taekyoung.onboardingchallenges.domain.member.dto.SignupRequest;
import com.taekyoung.onboardingchallenges.domain.member.dto.SignupResponse;
import com.taekyoung.onboardingchallenges.domain.member.model.Member;
import com.taekyoung.onboardingchallenges.domain.member.repository.MemberRepository;
import com.taekyoung.onboardingchallenges.infra.security.jwt.JwtHelper;
import org.hibernate.boot.model.naming.IllegalIdentifierException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtHelper jwtHelper;

    private MemberService(MemberRepository memberRepository, PasswordEncoder passwordEncoder, JwtHelper jwtHelper) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtHelper = jwtHelper;
    }

    public SignupResponse signup(SignupRequest request) {
        if(!memberRepository.existsByUsername(request.username())) {
            return memberRepository.save(
                    new Member(
                            request.username(),
                            request.nickname(),
                            passwordEncoder.encode(request.password())
                    )).toResponse();
        }
        else throw new IllegalIdentifierException("중복된 아이디입니다.");
    }

    public SignResponse sign(SignRequest request) {
        Member member = memberRepository.findByUsername(request.username()).orElseThrow(() -> new RuntimeException("회원정보가 없습니다."));
        if(passwordEncoder.matches(request.password(), member.getPassword())) {
            return new SignResponse(
                    jwtHelper.generateAccessToken(
                            member.getUsername(),
                            member.getAuthorityName()
                    )
            );
        } else throw new IllegalIdentifierException("비밀번호가 일치하지 않습니다.");
    }
}
