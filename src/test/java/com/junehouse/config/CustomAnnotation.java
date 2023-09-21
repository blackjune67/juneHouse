package com.junehouse.config;

import org.springframework.security.test.context.support.WithSecurityContext;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = CustomMockSecurityContext.class)
public @interface CustomAnnotation {
    String name() default "최하준";
    String email() default "fnffn0607@naver.com";
    String password() default "a1234";

    String role() default  "ROLE_ADMIN";
}
