package com.junehouse.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PostSearch {
    private static final int MAX_SIZE = 2000;
    // * 페이지 자체의 수
    @Builder.Default
    private Integer page = 1;
    // * 페이지 게시물의 수
    @Builder.Default
    private Integer size = 10;
    public long getOffset() {
        return ((long) (Math.max(1, page) - 1) * Math.min(size, MAX_SIZE));
    }
}
