package com.junehouse.controller;

import com.junehouse.config.AppConfig;
import com.junehouse.request.Login;
import com.junehouse.response.SessionResponse;
import com.junehouse.service.AuthService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.codec.binary.Base64;
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
//        String accessToken = authService.signin(login);
        Long memberId = authService.signin(login);

        Calendar cal = Calendar.getInstance();
//        cal.add(Calendar.DATE,1); //만료일 1일
        cal.add(Calendar.HOUR,1); //만료일 1시간

        // * JWT 인증
//        SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256); // ! HS256 key 값을 고정하도록 변경
//        String secretString = Encoders.BASE64.encode(key.getEncoded());
        SecretKey secretKey = Keys.hmacShaKeyFor(Base64.decodeBase64(appConfig.getJwtKey()));

        String jws = Jwts.builder()
                .setSubject(String.valueOf(memberId))
                .signWith(secretKey)
                .setIssuedAt(new Date())
                .setExpiration(new Date(cal.getTimeInMillis()))
                .compact();

        /*ResponseCookie responseCookie = ResponseCookie.from("SESSION", accessToken)
                .domain("localhost") // todo 서버 환경에 따른 분리 필요
                .path("/")
                .httpOnly(true)
                .secure(false)
                .maxAge(Duration.ofDays(30))
                .sameSite("Strict")
                .build();

        log.info("==> cookie={}", responseCookie.toString());*/

        /*return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, responseCookie.toString())
                .build();*/
//        return jws;
        return new SessionResponse(jws);
    }
}
