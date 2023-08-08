package com.junehouse.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

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

    // * AuthInterceptor를 이용해 인증처리 및 경로에 대한 패턴을 지정한다.
    /*@Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AuthInterceptor())
                .excludePathPatterns("/error", "favicon.ico");
    }*/

    // * ArgumentResolver를 이용(인증DTO)해서 인증처리를 한다.
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new AuthResolver());
    }
}
