package com.junehouse.response;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.Size;

/*
* 서비스 정책에 맞는 클래스
* */
@Getter
public class PostResponse {
    private final Long id;
    private final String title;
    private final String content;

    /*
    * 제목이 열 글자 제한을 둔 서비스 정책
    * */
    @Builder
    public PostResponse(Long id, String title, String content) {
        this.id = id;
        this.title = title.substring(0, Math.min(title.length(), 10));
        this.content = content;
    }
}
