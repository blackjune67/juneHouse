package com.junehouse.service;

import com.junehouse.crypto.PasswordEncoder;
import com.junehouse.domain.Member;
import com.junehouse.exception.AlreadyExistsEmailException;
import com.junehouse.repository.MemberRepository;
import com.junehouse.request.Signup;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    // * 회원가입
    public void signup(Signup signup) {
        Optional<Member> memberOptional = memberRepository.findByEmail(signup.getEmail());
        if(memberOptional.isPresent()) {
            throw new AlreadyExistsEmailException();
        }

//        PasswordEncoder passwordEncoder = new PasswordEncoder();
        String encodePassword = passwordEncoder.encrypt(signup.getPassword());

        Member member = Member.builder()
                .name(signup.getName())
                .email(signup.getEmail())
                .password(encodePassword)
                .build();

        memberRepository.save(member);
    }
}
