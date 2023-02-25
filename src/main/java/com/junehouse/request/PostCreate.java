package com.junehouse.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.beans.factory.annotation.Required;

import javax.validation.constraints.NotBlank;

@ToString
@Getter
public class PostCreate {

    @NotBlank(message = "제목은 필수입니다!")
    public String title;
    @NotBlank(message = "내용은 필수입니다!")
    public String content;

    @Builder
    @JsonCreator
    public PostCreate(@JsonProperty("title") String title, @JsonProperty("content") String content) {
        this.title = title;
        this.content = content;
    }
}