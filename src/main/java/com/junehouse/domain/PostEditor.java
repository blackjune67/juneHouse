package com.junehouse.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
public class PostEditor {
    private final String title;
    private final String content;

    @Builder
    public PostEditor(String title, String content) {
        this.title = title != null ? title : this.getTitle();
        this.content = content;
    }
}
