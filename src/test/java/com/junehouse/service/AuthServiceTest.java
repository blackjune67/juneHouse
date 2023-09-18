package com.junehouse.service;

import com.junehouse.domain.Member;
import com.junehouse.exception.AlreadyExistsEmailException;
import com.junehouse.repository.MemberRepository;
import com.junehouse.request.Signup;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AuthServiceTest {

    @Autowired
    private AuthService authService;

    @Autowired
    private MemberRepository memberRepository;

    @BeforeEach
    void clean() {
        memberRepository.deleteAll();
    }

    @Test
    @DisplayName("회원가입 성공")
    public void test01() {
        PasswordEncoder passwordEncoder = new PasswordEncoder();
        // given
        Signup signup = Signup.builder()
                .email("june001@naver.com")
                .password("a12345")
                .name("최하준")
                .build();

        // when
        authService.signup(signup);

        // then
        assertEquals(1, memberRepository.count());

        Member member = memberRepository.findAll().iterator().next();
        assertEquals("june001@naver.com", member.getEmail());
        assertEquals("최하준", member.getName());
        assertTrue(passwordEncoder.match("a12345", member.getPassword()));
//        assertNotNull(member.getName());
//        assertNotEquals("a12345", member.getPassword());
    }

    @Test
    @DisplayName("회원가입 중복 이메일")
    public void test02() {
        // given
        Member member01 = Member.builder()
                .email("fnffn0607@naver.com")
                .password("a1234")
                .name("최하준")
                .build();

        memberRepository.save(member01);

        Signup signup = Signup.builder()
                .email("fnffn0607@naver.com")
                .password("a1234")
                .name("최하준")
                .build();

        // expected
        assertThrows(AlreadyExistsEmailException.class, () -> authService.signup(signup));
    }
}