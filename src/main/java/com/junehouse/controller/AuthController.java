package com.junehouse.controller;

import com.junehouse.config.AppConfig;
import com.junehouse.request.Signup;
import com.junehouse.response.AuthResponse;
import com.junehouse.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final AppConfig appConfig;

    @GetMapping("/auth/login")
    public String login() {
        return "로그인 페이지";
    }

    @PostMapping("/auth/signup")
    public AuthResponse signup(@RequestBody Signup signup) {
//        authService.signup(signup);
        return authService.signup(signup);
    }
}
