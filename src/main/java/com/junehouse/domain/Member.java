package com.junehouse.domain;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
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
    private List<Session> sessions = new ArrayList<>();
    public Session addSession() {
        Session session = Session.builder()
                .member(this)
                .build();
        sessions.add(session);
        return session;
    }
    @Builder
    public Member(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }
}
