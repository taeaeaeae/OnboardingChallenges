package com.taekyoung.onboardingchallenges.mock;

import org.springframework.security.test.context.support.WithSecurityContext;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = WithCustomMockMemberSecurityContextFactory.class)
public @interface WithCustomMockMember {
    String username() default "leetaekyoung";
}
