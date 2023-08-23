package com.junehouse.service;

import com.junehouse.domain.Member;
import com.junehouse.domain.Session;
import com.junehouse.exception.AlreadyExistsEmailException;
import com.junehouse.exception.InvalidSign;
import com.junehouse.repository.MemberRepository;
import com.junehouse.request.Login;
import com.junehouse.request.Signup;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

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

    public void signup(Signup signup) {
        Optional<Member> memberOptional = memberRepository.findByEmail(signup.getEmail());
        if(memberOptional.isPresent()) {
            throw new AlreadyExistsEmailException();
        }

        Member member = Member.builder()
                .name(signup.getName())
                .email(signup.getEmail())
                .password(signup.getPassword())
                .build();

        memberRepository.save(member);
    }
}
