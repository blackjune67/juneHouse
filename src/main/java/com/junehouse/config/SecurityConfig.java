package com.junehouse.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.junehouse.config.handler.Http401Handler;
import com.junehouse.config.handler.Http403Handler;
import com.junehouse.config.handler.LoginFailHandler;
import com.junehouse.config.handler.SuccessHandler;
import com.junehouse.domain.Member;
import com.junehouse.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity(debug = true)
@Slf4j
@RequiredArgsConstructor
public class SecurityConfig {

    private final ObjectMapper objectMapper;

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring()
                .requestMatchers("/favicon.ico", "error")
                .requestMatchers(PathRequest.toH2Console());
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .authorizeHttpRequests()
                .requestMatchers("/auth/login").permitAll()
                .requestMatchers("/auth/signup").permitAll()
                .requestMatchers("/user").hasAnyRole("USER")
                .requestMatchers("/admin").hasAnyRole("ADMIN")
//                .requestMatchers("/user").hasAnyRole("USER", "ADMIN")
//                .requestMatchers("/admin").hasRole("ADMIN")
//                .requestMatchers("/admin").access(new WebExpressionAuthorizationManager("hasRole('ADMIN') AND hasAuthority('WRITE')"))
                .anyRequest().authenticated()
                .and()
                .formLogin()
                    .loginPage("/auth/login")
                    .loginProcessingUrl("/auth/login")
                    .usernameParameter("username")
                    .passwordParameter("password")
                    .defaultSuccessUrl("/")
                .successHandler(new SuccessHandler(objectMapper))
                .failureHandler(new LoginFailHandler(objectMapper))
                .and()
                .exceptionHandling(e -> {
                    e.accessDeniedHandler(new Http403Handler(objectMapper));
                    e.authenticationEntryPoint(new Http401Handler(objectMapper));
                })
                .rememberMe(rm -> rm.rememberMeParameter("remember")
                        .alwaysRemember(false)
                        .tokenValiditySeconds(2592000)
                )
                .csrf(AbstractHttpConfigurer::disable)
                .build();
    }

    @Bean
    public UserDetailsService userDetailsService(MemberRepository memberRepository) {
        // * DB
        return username -> {
            Member member = memberRepository.findByEmail(username).orElseThrow(
                    () -> new UsernameNotFoundException(username + "를 찾을 수 없습니다."));
            return new UserPrincipal(member);
        };

        /*InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        UserDetails user = User
                .withUsername("june")
                .password("1234")
                .roles("ADMIN")
                .build();
        manager.createUser(user);
        return manager;*/
    }

    // * 암호화 건너뛰게끔
    /*@Bean
    public PasswordEncoder encoder() {
        return NoOpPasswordEncoder.getInstance();
    }*/

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new SCryptPasswordEncoder(16, 8, 1, 32, 64);
    }
}
