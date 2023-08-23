package com.junehouse.service;

import com.junehouse.crypto.PasswordEncoder;
import com.junehouse.domain.Member;
import com.junehouse.exception.AlreadyExistsEmailException;
import com.junehouse.exception.InvalidSign;
import com.junehouse.repository.MemberRepository;
import com.junehouse.request.Login;
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
        assertNotNull(member.getName());
        assertNotEquals("a12345", member.getPassword());
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

    @Test
    @DisplayName("로그인 성공")
    public void test03() {
        // given
        PasswordEncoder passwordEncoder = new PasswordEncoder();
        String password = passwordEncoder.encrypt("ca009898*");
        Member member = Member.builder()
                .email("june@naver.com")
                .password(password)
                .name("최하준")
                .build();

        memberRepository.save(member);

        Login login = Login.builder()
                .email("june@naver.com")
                .password("ca009898*")
                .build();

        Long memberId = authService.signin(login);

        // when

        // then
        assertNotNull(memberId);
    }

    @Test
    @DisplayName("로그인 비밀번호 실패 시")
    public void test04() {
        // given
        Signup signup = Signup.builder()
                .email("june@naver.com")
                .password("ca009898*")
                .name("최하준")
                .build();
        authService.signup(signup);

        Login login = Login.builder()
                .email("june@naver.com")
                .password("ca009898!!")
                .build();

        // expected
        assertThrows(InvalidSign.class, () -> authService.signin(login));
    }
}