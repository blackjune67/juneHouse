package com.junehouse.domain;


import lombok.*;

import jakarta.persistence.*;

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
    @ManyToOne
    @JoinColumn
    private Member member;

    @Builder
    public Post(String title, String content) {
        this.title = title;
        this.content = content;
    }
    // * 방법 1
    public PostEditor.PostEditorBuilder toEditor() {
        return PostEditor.builder()
                .title(title)
                .content(content);
    }
    public void edit(PostEditor postEditor) {
        title = postEditor.getTitle();
        content = postEditor.getContent();
    }
    // * 방법 2
    public void edit2(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
