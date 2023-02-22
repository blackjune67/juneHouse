package com.junehouse.controller;

import com.junehouse.request.PostCreate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RestController
public class PostController {
    // SSR => jsp, thymeleaf
    // SPA => vue + SSR = nuxt.js, react + SSR = next.js

    // * GET, POST, PUT, PATCH, DELETE, OPTIONS, HEAD, TRACE, CONNECT
    @PostMapping("/posts")
//    public String post(@RequestParam String title, @RequestParam String content) {
//    public String post(@RequestParam Map<String, String> params) {
    public String post(@RequestBody PostCreate params) {
//        log.info("title = " + title + "content = " + content);
//        log.info("params = {} " + params);
        log.info(">>>>>>>> " + params.toString());
        return "hello";
    }

}
