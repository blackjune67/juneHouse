package com.junehouse.controller;

import com.junehouse.config.dto.UserSession;
import com.junehouse.request.PostCreate;
import com.junehouse.request.PostEdit;
import com.junehouse.request.PostSearch;
import com.junehouse.response.PostResponse;
import com.junehouse.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class PostController {
    // SSR => jsp, thymeleaf
    // SPA => vue + SSR = nuxt.js, react + SSR = next.js
    private final PostService postService;

    @GetMapping("/foo")
    public Long foo(UserSession userSession) {
        log.info("==> {}", userSession.id);
        return userSession.id;
    }

    @GetMapping("/bar")
    public String bar(UserSession userSession) {
        return "인증이 필요한 페이지입니다.";
    }


    // * GET, POST, PUT, PATCH, DELETE, OPTIONS, HEAD, TRACE, CONNECT
    @PostMapping("/posts")
    public void post(@RequestBody @Valid PostCreate request) {
        // * CASE_1 : 저장한 데이터 Entity -> response로 응답하기
        // * CASE_2 : 저장한 데이터의 primary_id -> response로 응답하기
        // * CASE_3 : 응답 필요 없음 -> 클라이언트에서 모든 POST(글) 데이터 context를 관리함.
        request.validate();
        postService.write(request);
    }

    @GetMapping("/posts/{postId}")
    public PostResponse get(@PathVariable Long postId) {
        return postService.get(postId);
    }

    // * @PageableDefault (기본10개)
    @GetMapping("/posts")
    public List<PostResponse> getList(@ModelAttribute PostSearch postSearch) {
        return postService.getList(postSearch);
    }

    @PatchMapping("/posts/{postId}")
    public PostResponse edit(@PathVariable Long postId, @RequestBody @Valid PostEdit request) {
        return postService.edit(postId, request);
    }

    @DeleteMapping("/posts/{postId}")
    public void delete(@PathVariable Long postId) {
        postService.delete(postId);
    }

}
