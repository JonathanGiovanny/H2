package com.jjo.h2.model.security;

import java.util.Set;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@NodeEntity
public class Role {

  @Id
  @GeneratedValue
  private Long id;

  private String name;

  @Relationship(type = "HAS_ACCESS_TO")
  private Set<AccessData> privileges;
}
