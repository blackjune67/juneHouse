package com.junehouse.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

// * 회원 Entity
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
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "member")
    private List<Post> posts;

    @Builder
    public Member(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }
}
