package com.junehouse.repository;

import com.junehouse.domain.Member;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

// * 로그인 Repository
public interface MemberRepository extends CrudRepository<Member, Long> {
    Optional<Member> findByEmailAndPassword(String email, String password);
    Optional<Member> findByEmail(String email);
}
