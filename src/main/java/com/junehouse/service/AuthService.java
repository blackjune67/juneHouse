package com.junehouse.service;

import com.junehouse.domain.Member;
import com.junehouse.exception.AlreadyExistsEmailException;
import com.junehouse.repository.MemberRepository;
import com.junehouse.request.Signup;
import com.junehouse.response.AuthResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    // * 회원가입
    public AuthResponse signup(Signup signup) {
        Optional<Member> memberOptional = memberRepository.findByEmail(signup.getEmail());
        if(memberOptional.isPresent()) {
            throw new AlreadyExistsEmailException();
        }

//        PasswordEncoder passwordEncoder = new PasswordEncoder();
//        String encodePassword = passwordEncoder.encrypt(signup.getPassword());
        String encodePassword = passwordEncoder.encode(signup.getPassword());

        Member member = Member.builder()
                .name(signup.getName())
                .email(signup.getEmail())
                .password(encodePassword)
                .build();

        memberRepository.save(member);
        return AuthResponse.builder()
                .code("200")
                .email(member.getEmail())
                .message("회원가입을 축하합니다!")
                .build();
    }
}
