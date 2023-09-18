package com.junehouse.config;

import com.junehouse.domain.Member;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.List;

public class UserPrincipal extends User {
    private final Long userId;

    public UserPrincipal(Member member) {
        super(member.getName(), member.getPassword(),
                List.of(
                        new SimpleGrantedAuthority("ROLE_ADMIN"),
                        new SimpleGrantedAuthority("WRITE")
                ));
        this.userId = member.getId();
    }

    public Long getUserId() {
        return userId;
    }
}
