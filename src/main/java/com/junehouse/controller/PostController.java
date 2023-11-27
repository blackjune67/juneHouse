package com.junehouse.controller;

import com.junehouse.config.UserPrincipal;
import com.junehouse.request.PostCreate;
import com.junehouse.request.PostEdit;
import com.junehouse.request.PostSearch;
import com.junehouse.response.PostResponse;
import com.junehouse.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
public class PostController {
    // SSR => jsp, thymeleaf
    // SPA => vue + SSR = nuxt.js, react + SSR = next.js
    private final PostService postService;

    @GetMapping("/user_info")
    public Map<String, Object> getUser() {
        HashMap<String, Object> map = new HashMap<>();
        HashMap<String, Object> data = new HashMap<>();
        HashMap<String, Object> user = new HashMap<>();

        data.put("email", "bbaa22@naver.com");
        data.put("phone", "15290788137");
        data.put("username", "bbaa22@naver.com");
        data.put("id", "601d85900f43923hffbcs");
        data.put("token", "4v8acea-6a89-2a2ebc-10802-9ac19003");

        user.put("language", "en");
        user.put("user", data);

        map.put("error", 0);
        map.put("msg", "OK");
//        map.put("data", user);

        log.info("getUser");
        return map;
    }

    // * GET, POST, PUT, PATCH, DELETE, OPTIONS, HEAD, TRACE, CONNECT
    @PostAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/posts")
    public void post(@AuthenticationPrincipal UserPrincipal userPrincipal, @RequestBody @Valid PostCreate request) {
        // * CASE_1 : 저장한 데이터 Entity -> response로 응답하기
        // * CASE_2 : 저장한 데이터의 primary_id -> response로 응답하기
        // * CASE_3 : 응답 필요 없음 -> 클라이언트에서 모든 POST(글) 데이터 context를 관리함.
        request.validate();
        postService.write(userPrincipal.getUserId(), request);
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

    @PostAuthorize("hasRole('ROLE_ADMIN')")
    @PatchMapping("/posts/{postId}")
    public PostResponse edit(@PathVariable Long postId, @RequestBody @Valid PostEdit request) {
        return postService.edit(postId, request);
    }

//    @PostAuthorize("hasRole('ROLE_ADMIN')")
    @PreAuthorize("hasRole('ROLE_ADMIN') && hasPermission(#postId, 'POST', 'DELETE')")
    @DeleteMapping("/posts/{postId}")
    public void delete(@PathVariable Long postId) {
        postService.delete(postId);
    }
}
