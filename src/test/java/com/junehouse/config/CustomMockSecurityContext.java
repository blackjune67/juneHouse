package com.junehouse.config;

import com.junehouse.domain.Member;
import com.junehouse.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import java.util.List;

@RequiredArgsConstructor
public class CustomMockSecurityContext implements WithSecurityContextFactory<CustomAnnotation> {
    private final MemberRepository memberRepository;

    @Override
    public SecurityContext createSecurityContext(CustomAnnotation annotation) {
        var user = Member
                .builder()
                .name(annotation.name())
                .email(annotation.email())
                .password(annotation.password())
                .build();
        memberRepository.save(user);

        var userPrincipal = new UserPrincipal(user);
        var role = new SimpleGrantedAuthority("ROLE_ADMIN");
        var authenticationToken = new UsernamePasswordAuthenticationToken(
                userPrincipal,
                user.getPassword(),
                List.of(role));

        var context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authenticationToken);

        return context;
    }
}
