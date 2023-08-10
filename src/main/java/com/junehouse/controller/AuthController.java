package com.junehouse.controller;

import com.junehouse.repository.MemberRepository;
import com.junehouse.request.Login;
import com.junehouse.response.SessionResponse;
import com.junehouse.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthController {
    private final MemberRepository userRepository;
    private final AuthService authService;

    @PostMapping("/auth/login")
    public SessionResponse login(@RequestBody Login login) {
        log.info("==> {} ", login);
        return new SessionResponse(authService.signin(login));
    }
}
