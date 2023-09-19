package com.junehouse.config;

import com.junehouse.domain.Member;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.List;

public class UserPrincipal extends User {
    private final Long userId;

    // * role : 역할 -> 관리자, 사용자, 매니저 ..
    // * authority : 권한 -> 글쓰기, 글 읽기 등..

    public UserPrincipal(Member member) {
        super(member.getEmail(), member.getPassword(),
                List.of(
                        new SimpleGrantedAuthority("ROLE_USER")
                        /*new SimpleGrantedAuthority("ROLE_ADMIN"),
                        new SimpleGrantedAuthority("WRITE")*/
                ));
        this.userId = member.getId();
    }

    public Long getUserId() {
        return userId;
    }
}
