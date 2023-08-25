package com.junehouse.controller;

import com.junehouse.config.AppConfig;
import com.junehouse.request.Login;
import com.junehouse.request.Signup;
import com.junehouse.response.SessionResponse;
import com.junehouse.service.AuthService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.crypto.SecretKey;
import java.util.Calendar;
import java.util.Date;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final AppConfig appConfig;

    @PostMapping("/auth/login")
    public SessionResponse login(@RequestBody Login login) {
        Long memberId = authService.signin(login);

        Calendar cal = Calendar.getInstance();
        // * cal.add(Calendar.DATE,1); //만료일 1일
        cal.add(Calendar.HOUR,1); //만료일 1시간

        // * JWT 인증
        SecretKey secretKey = Keys.hmacShaKeyFor(appConfig.getJwtKey());

        String jws = Jwts.builder()
                .setSubject(String.valueOf(memberId))
                .signWith(secretKey)
                .setIssuedAt(new Date())
                .setExpiration(new Date(cal.getTimeInMillis()))
                .compact();
        return new SessionResponse(jws);
    }

    @PostMapping("/auth/signup")
    public void signup(@RequestBody Signup signup) {
        authService.signup(signup);
    }
}
