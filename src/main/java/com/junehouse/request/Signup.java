package com.junehouse.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Signup {
    private String email;
    private String name;
    private String password;

    @Builder
    public Signup(String email, String name, String password) {
        this.email = email;
        this.name = name;
        this.password = password;
    }
}