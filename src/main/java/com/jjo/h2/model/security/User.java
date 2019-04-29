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
import com.googlecode.jmapper.annotations.JMap;
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
  @JMap
  private Long id;

  @JMap
  private String username;

  @JMap
  private String password;

  @JMap
  private String email;

  @JMap
  @CreatedDate
  private LocalDateTime createdDate;

  @JMap
  private LocalDate passwordDate;

  @JMap
  private Blob profilePic;

  @JMap
  @Relationship(type = "HAS")
  private Set<Role> roles;
}
