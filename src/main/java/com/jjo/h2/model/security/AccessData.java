package com.jjo.h2.model.security;

import java.io.Serializable;
import org.neo4j.ogm.annotation.EndNode;
import org.neo4j.ogm.annotation.RelationshipEntity;
import org.neo4j.ogm.annotation.StartNode;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@RelationshipEntity(type = "HAS_ACCESS_TO")
public class AccessData implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = -396978327961976257L;

  private Double order;

  @StartNode
  private Role role;

  @EndNode
  private Privilege privilege;
}
