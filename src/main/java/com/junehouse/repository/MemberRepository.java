package com.junehouse.repository;

import com.junehouse.domain.Member;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

// * 로그인 Repository
public interface MemberRepository extends CrudRepository<Member, Long> {
    List<Member> findByEmailAndPassword(String email, String password);
}
