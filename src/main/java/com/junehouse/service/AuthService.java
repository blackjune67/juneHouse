package com.junehouse.service;

import com.junehouse.domain.Member;
import com.junehouse.exception.InvalidSign;
import com.junehouse.repository.MemberRepository;
import com.junehouse.request.Login;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final MemberRepository memberRepository;

    public Member signin(Login login) {
        Member member = memberRepository.findByEmailAndPassword(login.getEmail(), login.getPassword())
                .orElseThrow(InvalidSign::new);
        return member;
    }
}
