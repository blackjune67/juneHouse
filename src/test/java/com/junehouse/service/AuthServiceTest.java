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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
        // given
        Signup signup = Signup.builder()
                .email("june@naver.com")
                .name("최하준")
                .build();

        // when
        authService.signup(signup);

        // then
        assertEquals(1, memberRepository.count());

        Member member = memberRepository.findAll().iterator().next();
        assertEquals("june@naver.com", member.getEmail());
        assertEquals("최하준", member.getName());
        assertEquals(signup.getPassword(), member.getPassword());
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