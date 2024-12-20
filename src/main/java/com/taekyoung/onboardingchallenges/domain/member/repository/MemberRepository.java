package com.taekyoung.onboardingchallenges.domain.member.repository;

import com.taekyoung.onboardingchallenges.domain.member.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Member findByUsername(String username);
    boolean existsByUsername(String username);
}
