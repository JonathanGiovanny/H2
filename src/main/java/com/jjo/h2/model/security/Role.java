package com.jjo.h2.model.security;

import java.io.Serializable;
import java.util.Set;
import java.util.stream.Collectors;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;
import com.googlecode.jmapper.annotations.JMap;
import com.googlecode.jmapper.annotations.JMapConversion;
import com.jjo.h2.dto.security.PrivilegeDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@NodeEntity
public class Role implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 2618634237817935601L;

  @Id
  @JMap
  @GeneratedValue
  private Long id;

  @JMap
  private String name;

  @JMap
  @Relationship(type = "HAS_ACCESS_TO")
  private Set<AccessData> privileges;

  @JMapConversion(from = {"privileges"}, to = {"privileges"})
  public Set<PrivilegeDTO> privEntityToDTO(Set<AccessData> privilegeEntity) {
    return privilegeEntity.stream().map(p -> {
      PrivilegeDTO priv = new PrivilegeDTO();
      priv.setId(p.getPrivilege().getId());
      priv.setName(p.getPrivilege().getName());
      priv.setDescription(p.getPrivilege().getDescription());
      return priv;
    }).collect(Collectors.toSet());
  }
}
