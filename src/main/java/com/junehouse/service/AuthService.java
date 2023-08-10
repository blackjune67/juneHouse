package com.junehouse.service;

import com.junehouse.domain.Member;
import com.junehouse.exception.InvalidSign;
import com.junehouse.repository.MemberRepository;
import com.junehouse.request.Login;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final MemberRepository memberRepository;

    public List<Member> signin(Login login) {
        List<Member> memberList = Optional.ofNullable(memberRepository.findByEmailAndPassword(login.getEmail(), login.getPassword()))
                .orElseThrow(() -> new InvalidSign());
        return memberList;
    }
}
