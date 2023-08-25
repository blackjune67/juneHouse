package com.junehouse.response;

import com.junehouse.domain.Post;
import lombok.Builder;
import lombok.Getter;

/*
* 서비스 정책에 맞는 클래스
* */
@Getter
public class PostResponse {
    private final Long id;
    private final String title;
    private final String content;
    /*
    * stream builder 반복적인 부분을 Response 응답객체로 분리
    * 생성자 오버로딩을 이용함.
    * */
    public PostResponse(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
    }
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
