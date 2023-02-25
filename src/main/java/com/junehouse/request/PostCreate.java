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


    // * private PostCreate() {}

    @Builder
    @JsonCreator
    public PostCreate(@JsonProperty("title") String title, @JsonProperty("content") String content) {
        this.title = title;
        this.content = content;
    }

    /*@Builder
    public PostCreate(String title, String content) {
        this.title = title;
        this.content = content;
    }*/

    /*
    * 빌더의 장점
    * - 가독성에 좋다. (값 생성에 대한 유연함)
    * - 필요한 값만 받을 수 있다. -> 오버로딩 가능한 조건?
    * - 객체의 불변성
    * */
}