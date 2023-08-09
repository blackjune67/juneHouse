package com.junehouse.controller;

import com.junehouse.domain.Member;
import com.junehouse.exception.InvalidSign;
import com.junehouse.repository.MemberRepository;
import com.junehouse.request.Login;
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

    @PostMapping("/auth/login")
    public Member login(@RequestBody Login login) {
        // json
        log.info("==> {} ", login);
        // db
        Member member = userRepository.findByEmailAndPassword(login.getEmail(), login.getPassword())
                .orElseThrow(InvalidSign::new);
        // 토큰을 응답
        return member;
    }
}
