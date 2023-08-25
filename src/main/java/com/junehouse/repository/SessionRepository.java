package com.junehouse.repository;

import com.junehouse.domain.Session;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

// * 세션 Repository
public interface SessionRepository extends CrudRepository<Session, Long> {
    Optional<Session> findByAccessToken(String accessToken);
}