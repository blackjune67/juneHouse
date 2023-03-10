package com.junehouse.controller;

import com.junehouse.domain.Post;
import com.junehouse.request.PostCreate;
import com.junehouse.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

@Slf4j
@RestController
@RequiredArgsConstructor
public class PostController {
    // SSR => jsp, thymeleaf
    // SPA => vue + SSR = nuxt.js, react + SSR = next.js
    private final PostService postService;

    // * GET, POST, PUT, PATCH, DELETE, OPTIONS, HEAD, TRACE, CONNECT
    @PostMapping("/posts")
    public void post(@RequestBody @Valid PostCreate request) {
        // * CASE_1 : 저장한 데이터 Entity -> response로 응답하기
        // * CASE_2 : 저장한 데이터의 primary_id -> response로 응답하기
        // * CASE_3 : 응답 필요 없음 -> 클라이언트에서 모든 POST(글) 데이터 context를 관리함.
        postService.write(request);
//        return Map.of();
    }

    @GetMapping("/posts/{postId}")
    public Post select(@PathVariable(name = "postId") Long id) {
        return postService.get(id);
    }
}
