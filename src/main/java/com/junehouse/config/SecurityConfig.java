package com.junehouse.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.junehouse.config.filter.EmailPasswordAuthFilter;
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
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.session.security.web.authentication.SpringSessionRememberMeServices;

@Slf4j
@Configuration
@EnableWebSecurity(debug = true)
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final ObjectMapper objectMapper;
    private final MemberRepository memberRepository;

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring()
                .requestMatchers("/favicon.ico", "error")
                .requestMatchers(PathRequest.toH2Console());
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests()
                .requestMatchers("/auth/login").permitAll()
                .requestMatchers("/auth/signup").permitAll()
                /*.requestMatchers("/user").hasRole("USER")
                .requestMatchers("/admin").hasRole("ADMIN")*/
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(emailPasswordAuthFilter(), UsernamePasswordAuthenticationFilter.class)
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
    public AuthenticationManager authenticationManager() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService(memberRepository));
        provider.setPasswordEncoder(passwordEncoder());
        return new ProviderManager(provider);
    }

    @Bean
    public EmailPasswordAuthFilter emailPasswordAuthFilter() {
        EmailPasswordAuthFilter emailPasswordAuthFilter = new EmailPasswordAuthFilter(objectMapper);
        emailPasswordAuthFilter.setAuthenticationManager(authenticationManager());
        emailPasswordAuthFilter.setAuthenticationSuccessHandler(new SuccessHandler(objectMapper));
        emailPasswordAuthFilter.setAuthenticationFailureHandler(new LoginFailHandler(objectMapper));
        emailPasswordAuthFilter.setSecurityContextRepository(new HttpSessionSecurityContextRepository());

        SpringSessionRememberMeServices rememberMeServices = new SpringSessionRememberMeServices();
        rememberMeServices.setAlwaysRemember(true);
        rememberMeServices.setValiditySeconds(3600 * 24 * 30); // * 한달 세션
        emailPasswordAuthFilter.setRememberMeServices(rememberMeServices);
        return emailPasswordAuthFilter;
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
