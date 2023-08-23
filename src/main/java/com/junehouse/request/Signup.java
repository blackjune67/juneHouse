package com.junehouse.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;

@Getter
@Setter
@NoArgsConstructor
public class Signup {
    private String email;
    private String name;
    private String password;

    // * 비밀번호 암호화
    public void setPassword(String password) {
        SCryptPasswordEncoder sCryptPasswordEncoder = new SCryptPasswordEncoder(16, 8, 1, 32, 64);
        this.password = sCryptPasswordEncoder.encode(password);
    }

    @Builder
    public Signup(String email, String name, String password) {
        this.email = email;
        this.name = name;
        this.password = password;
    }
}