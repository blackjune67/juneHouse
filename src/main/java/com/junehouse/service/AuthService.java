package com.junehouse.service;

import com.junehouse.crypto.PasswordEncoder;
import com.junehouse.domain.Member;
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
    private final PasswordEncoder passwordEncoder;

    // * 로그인
    @Transactional
    public Long signin(Login login) {
        /*Member member = memberRepository.findByEmailAndPassword(login.getEmail(), login.getPassword())
                .orElseThrow(InvalidSign::new);*/
//        Session session = member.addSession();
//        return session.getAccessToken();
        Member member = memberRepository.findByEmail(login.getEmail())
                .orElseThrow(InvalidSign::new);

//        PasswordEncoder passwordEncoder = new PasswordEncoder();
        boolean matches = passwordEncoder.match(login.getPassword(), member.getPassword());

        if(!matches) {
            throw new InvalidSign();
        }

        return member.getId();
    }

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
