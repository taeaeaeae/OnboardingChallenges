package com.taekyoung.onboardingchallenges.domain.member.controller;

import com.taekyoung.onboardingchallenges.domain.member.dto.SignRequest;
import com.taekyoung.onboardingchallenges.domain.member.dto.SignResponse;
import com.taekyoung.onboardingchallenges.domain.member.dto.SignupRequest;
import com.taekyoung.onboardingchallenges.domain.member.dto.SignupResponse;
import com.taekyoung.onboardingchallenges.domain.member.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
@Tag(name = "회원 API")
public class MemberController {

    private final MemberService memberService;
    public MemberController(final MemberService memberService) {
        this.memberService = memberService;
    }

    @Operation(summary = "회원가입")
    @PostMapping("/signup")
    public ResponseEntity<SignupResponse> signup(@RequestBody final SignupRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(memberService.signup(request));
    }

    @Operation(summary = "로그인")
    @PostMapping("/sign")
    public ResponseEntity<SignResponse> sign(@RequestBody final SignRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(memberService.sign(request));
    }


}
