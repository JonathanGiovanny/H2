package com.jjo.h2.model.security;

import java.io.Serializable;
import java.sql.Blob;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.neo4j.repository.config.Neo4jAuditingEventListener;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@NodeEntity
@EntityListeners(Neo4jAuditingEventListener.class)
public class User implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 6532277996244003805L;

  @Id
  @GeneratedValue
  private Long id;

  private String username;

  private String password;

  private String email;

  private StatusEnum status;

  @CreatedDate
  private LocalDateTime createdDate;

  private LocalDate passwordDate;

  private transient Blob profilePic;

  @Relationship(type = "HAS")
  private Set<Role> roles;
}
