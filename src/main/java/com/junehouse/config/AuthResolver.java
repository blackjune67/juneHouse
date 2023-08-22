package com.junehouse.config;

import com.junehouse.config.dto.UserSession;
import com.junehouse.exception.Unauthorized;
import com.junehouse.repository.SessionRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Slf4j
@RequiredArgsConstructor
public class AuthResolver implements HandlerMethodArgumentResolver {

    private final SessionRepository sessionRepository;
    private final AppConfig appConfig;

//    private static final String KEY = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyIn0.NsqiK0Hi-XMfh--iaXWibKHMUszRhjQp5KRC96FBkRs";

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(UserSession.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        log.info("==> appconfig:{}", appConfig.toString());
//        HttpServletRequest servletRequest = webRequest.getNativeRequest(HttpServletRequest.class);
        String jws = webRequest.getHeader("Authorization");

        log.info("==> AUthResolver jws:{}", jws);
        if(jws == null || jws.equals("")) {
            log.error("jws value null");
            throw new Unauthorized();
        }

        // * 쿠키 validation
        /*Cookie[] cookies = servletRequest.getCookies();

        if (cookies.length == 0) {
            log.error("쿠키가 없습니다");
            throw new Unauthorized();
        }

        String accessToken = cookies[0].getValue();
        log.info("== value:{}", accessToken);

        Session byAccessToken = sessionRepository.findByAccessToken(accessToken)
                .orElseThrow(Unauthorized::new);*/

//        byte[] decodeKey = Base64.decodeBase64(appConfig.getJwtKey());

        try {
            Jws<Claims> claimsJws = Jwts.parserBuilder()
                    .setSigningKey(appConfig.getJwtKey())
                    .build()
                    .parseClaimsJws(jws);

            log.info("==> jws:{}", claimsJws);

            String id = claimsJws.getBody().getSubject();
            return new UserSession(Long.parseLong(id));
        } catch (JwtException e) {
            log.error("No ID value");
            throw new Unauthorized();
        }

//        return new UserSession(byAccessToken.getMember().getId());
    }
}
