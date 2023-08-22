package com.junehouse.service;

import com.junehouse.domain.Member;
import com.junehouse.domain.Session;
import com.junehouse.exception.InvalidSign;
import com.junehouse.repository.MemberRepository;
import com.junehouse.request.Login;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final MemberRepository memberRepository;

    @Transactional
    public Long signin(Login login) {
        Member member = memberRepository.findByEmailAndPassword(login.getEmail(), login.getPassword())
                .orElseThrow(InvalidSign::new);
        Session session = member.addSession();

//        return session.getAccessToken();
        return member.getId();
    }

    public void signup() {
    }
}
