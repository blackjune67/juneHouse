package com.junehouse.repository;

import com.junehouse.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// * 로그인 Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmailAndPassword(String email, String password);
    Optional<Member> findByEmail(String email);
}
