package com.junehouse.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    // * CORS 문제해결
    // * 모든 요청을 허용하도록 수정함
    @Override
    public void addCorsMappings(CorsRegistry registry) {
//        WebMvcConfigurer.super.addCorsMappings(registry);
        registry.addMapping("/**")
//                .allowedOrigins("*");
                .allowedOrigins("http://localhost:3000", "http://localhost:5173");
    }
}
