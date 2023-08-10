package com.junehouse.repository;

import com.junehouse.domain.Session;
import org.springframework.data.repository.CrudRepository;

// * 세션 Repository
public interface SessionRepository extends CrudRepository<Session, Long> {

}