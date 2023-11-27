package com.junehouse.config.handler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class RequestInterceptor implements HandlerInterceptor {
    public void readBody(HttpServletRequest request, HttpServletResponse response) {
        String requestBody = (String) request.getAttribute("requestBody");
    }
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        this.readBody(request, response);
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

}
