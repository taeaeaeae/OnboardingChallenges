package com.taekyoung.onboardingchallenges.controller;

import com.taekyoung.onboardingchallenges.domain.member.model.Member;
import com.taekyoung.onboardingchallenges.domain.member.repository.MemberRepository;
import com.taekyoung.onboardingchallenges.mock.WithCustomMockMember;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.yml")
public class MemberControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        Member member = new Member(
                        "taekyoung",
                        "my taekyoung",
                passwordEncoder.encode("password")
                );
        memberRepository.save(member);
    }

//    @Test
//    public void MemberSignSuccess() throws Exception {
//
//        String request = "{ \"username\": \"taekyoung\", \"password\": \"password\" }";
//
//        mockMvc.perform(post("/sign")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(request)
//                        .header("X-AUTH-TOKEN", "header"))
//                .andExpect(status().isOk());
//    }

    @Test
    @WithCustomMockMember
    public void MemberSignupSuccess() throws Exception {

        String request = "{ \"username\": \"newMember\", \"password\": \"password\", \"nickname\": \"nickname\" }";

        mockMvc.perform(post("/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request)
                        .header("X-AUTH-TOKEN", "header"))
                .andExpect(status().isCreated());
    }

}
