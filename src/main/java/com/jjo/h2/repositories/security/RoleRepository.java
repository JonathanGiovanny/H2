package com.jjo.h2.repositories.security;

import java.util.Set;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import com.jjo.h2.model.security.Role;

public interface RoleRepository extends Neo4jRepository<Role, Long> {

  Set<Role> findByName(String name);
}
