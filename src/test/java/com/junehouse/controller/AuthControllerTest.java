package com.junehouse.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.junehouse.domain.Member;
import com.junehouse.domain.Session;
import com.junehouse.repository.MemberRepository;
import com.junehouse.repository.SessionRepository;
import com.junehouse.request.Login;
import com.junehouse.service.AuthService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerTest {
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private AuthService authService;

    @BeforeEach
    void clean() {
        memberRepository.deleteAll();
    }

    @Test
    @DisplayName("로그인 성공 후 세션 1개 생성 테스트")
    void test01() throws Exception {
        //given
        Member member = Member.builder()
                .email("abc@naver.com")
                .password("1234")
                .build();

        memberRepository.save(member);

        Login login = Login.builder()
                .email("abc@naver.com")
                .password("1234")
                .build();

        String json = objectMapper.writeValueAsString(login);

        // expected
        mockMvc.perform(
                        post("/auth/login")
                                .contentType(APPLICATION_JSON)
                                .content(json)
                )
                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.code").value("400"))
//                .andExpect(jsonPath("$.message").value("잘못된 요청입니다."))
                .andDo(print());
    }

    @Test
    @DisplayName("로그인 생성 후 세션 테스트")
    @Transactional
    void test02() throws Exception {
        // given
        // * 회원가입 회원
        Member member = Member.builder()
                .email("abc@naver.com")
                .password("1234")
                .build();

        memberRepository.save(member);

        // * 회원가입을 한 정보로 로그인
        Login login = Login.builder()
                .email("abc@naver.com")
                .password("1234")
                .build();

        String json = objectMapper.writeValueAsString(login);

        // expected
        mockMvc.perform(post("/auth/login")
                        .contentType(APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andDo(print());

        /*Member signedInMember = memberRepository.findById(member.getId())
                .orElseThrow(RuntimeException::new);*/

        assertEquals(1L, sessionRepository.count());
        assertEquals(1L, member.getSessions().size());
    }

    @Test
    @DisplayName("로그인 성공 후 세션 테스트")
    void test03() throws Exception {
        // given
        // * 회원가입 회원
        Member member = Member.builder()
                .email("abc@naver.com")
                .password("1234")
                .build();

        memberRepository.save(member);

        // * 회원가입을 한 정보로 로그인
        Login login = Login.builder()
                .email("abc@naver.com")
                .password("1234")
                .build();

        String json = objectMapper.writeValueAsString(login);

        // expected
        mockMvc.perform(post("/auth/login")
                        .contentType(APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken", Matchers.notNullValue()))
                .andDo(print());
    }

    @Test
    @DisplayName("로그인 후 권한이 필요한 페이지 접속 /foo")
    void test04() throws Exception {
        // * 회원가입 회원
        Member member = Member.builder()
                .email("abc@naver.com")
                .password("1234")
                .build();

        Session session = member.addSession();

        memberRepository.save(member);

        // * 회원가입을 한 정보로 로그인
//        Login login = Login.builder()
//                .email("abc@naver.com")
//                .password("1234")
//                .build();
//
//        String json = objectMapper.writeValueAsString(login);

        mockMvc.perform(get("/foo")
                        .header("Authorization", session.getAccessToken())
                        .contentType(APPLICATION_JSON)
                )
                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.accessToken", Matchers.notNullValue()))
                .andDo(print());
    }

    @Test
    @DisplayName("로그인 후 검증되지 않은 세션값으로 권한 필요한 페이지 접속 불가능")
    void test05() throws Exception {
        // * 회원가입 회원
        Member member = Member.builder()
                .email("abc@naver.com")
                .password("1234")
                .build();

        Session session = member.addSession();

        memberRepository.save(member);

        mockMvc.perform(get("/bar")
                        .header("Authorization", session.getAccessToken() + "-00987")
                        .contentType(APPLICATION_JSON)
                )
                .andExpect(status().isUnauthorized())
                .andDo(print());
    }
}