package com.junehouse.config.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.junehouse.config.UserPrincipal;
import com.junehouse.response.AuthResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Slf4j
@RequiredArgsConstructor
public class SuccessHandler implements AuthenticationSuccessHandler {
    private final ObjectMapper objectMapper;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        String username = userPrincipal.getUsername();

        log.info("[로그인 성공] user={}", username);

        clearAuthenticationAttributes(request);

        // * SecurityContextHolder 에서 정보를 갖고 옴.
//        Object principal = SecurityContextHolder.getContext().getAuthentication().getName();
        AuthResponse.UserData userData = new AuthResponse.UserData(
                "bbaa22@naver.com",
                "15290788137",
                username,
                "601d85900f43923hffbcs",
                "4v8acea-6a89-2a2ebc-10802-9ac19003"
        );

        AuthResponse.User user = new AuthResponse.User("en", userData);

        AuthResponse authResponse = AuthResponse
                .builder()
                .data(user)
                .language("en")
                .error(0)
                .msg("OK")
                .code("200")
                .email(username)
                .message(username + " 님이 로그인에 성공했습니다.")
                .build();

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.setStatus(HttpServletResponse.SC_OK);
        objectMapper.writeValue(response.getWriter(), authResponse);
    }

    // * 로그인 실패 시 session에 남아있는 실패 이력 제거
    private void clearAuthenticationAttributes(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
        }
    }
}
