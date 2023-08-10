package com.junehouse.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

// * 로그인 유저 Entity
@Getter
@Entity
//@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String password;
    // * H2 database 에러로 인한 임시 제외
//    private LocalDateTime createdAt;

    @Builder
    public Member(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
