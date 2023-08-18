package com.junehouse.config;

import com.junehouse.config.dto.UserSession;
import com.junehouse.domain.Session;
import com.junehouse.exception.Unauthorized;
import com.junehouse.repository.SessionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Slf4j
@RequiredArgsConstructor
public class AuthResolver implements HandlerMethodArgumentResolver {

    private final SessionRepository sessionRepository;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(UserSession.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest servletRequest = webRequest.getNativeRequest(HttpServletRequest.class);

        if(servletRequest == null) {
            log.error("servletRequest null");
            throw new Unauthorized();
        }

        Cookie[] cookies = servletRequest.getCookies();

        if (cookies.length == 0) {
            log.error("쿠키가 없습니다");
            throw new Unauthorized();
        }

        String accessToken = cookies[0].getValue();
        log.info("== value:{}", accessToken);

        Session byAccessToken = sessionRepository.findByAccessToken(accessToken)
                .orElseThrow(Unauthorized::new);

        return new UserSession(byAccessToken.getMember().getId());
    }
}
