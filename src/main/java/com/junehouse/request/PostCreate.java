package com.junehouse.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Required;

import javax.validation.constraints.NotBlank;

@ToString
@Setter
@Getter
public class PostCreate {

    @NotBlank(message = "제목은 필수입니다!")
    public String title;
    @NotBlank(message = "내용은 필수입니다!")
    public String content;
}
