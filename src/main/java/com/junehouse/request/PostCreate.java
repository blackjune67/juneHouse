package com.junehouse.request;

import com.junehouse.exception.InvalidRequest;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@ToString
@Getter
@NoArgsConstructor
public class PostCreate {
    @NotBlank(message = "제목은 필수입니다!")
    public String title;
    @NotBlank(message = "내용은 필수입니다!")
    public String content;
    @Builder
    public PostCreate(String title, String content) {
        this.title = title;
        this.content = content;
    }
    public void validate() {
        if (title.contains("바보")) {
            throw new InvalidRequest("title", "제목에 특정단어가 들어가있습니다.");
        }
    }
}