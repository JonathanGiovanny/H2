package com.jjo.h2.model.security;

import org.neo4j.ogm.annotation.EndNode;
import org.neo4j.ogm.annotation.RelationshipEntity;
import org.neo4j.ogm.annotation.StartNode;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@RelationshipEntity(type = "HAS_ACCESS_TO")
public class AccessData {

  private Double order;

  @StartNode
  private Role role;

  @EndNode
  private Privilege privigele;
}
