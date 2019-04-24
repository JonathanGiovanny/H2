package com.jjo.h2.repositories.security;

import java.util.List;

import org.springframework.data.neo4j.repository.Neo4jRepository;

import com.jjo.h2.model.security.User;

public interface UserRepository extends Neo4jRepository<User, Long> {

	List<User> findByUsername(String username);
}
