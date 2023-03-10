package com.junehouse.domain;


import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;

    @Lob
    private String content;

//    private Post() {}

    @Builder
    public Post(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
