package com.junehouse.domain;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.*;

// * 로그인 유저 Entity
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
}
