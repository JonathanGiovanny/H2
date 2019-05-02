package com.jjo.h2.dto.security;

import java.util.Set;
import java.util.stream.Collectors;
import com.googlecode.jmapper.annotations.JMap;
import com.googlecode.jmapper.annotations.JMapConversion;
import com.jjo.h2.model.security.AccessData;
import com.jjo.h2.model.security.Privilege;
import lombok.Data;

@Data
public class RoleDTO {

  @JMap
  private Long id;

  @JMap
  private String name;

  @JMap
  private Set<PrivilegeDTO> privileges;

  @JMapConversion(from = {"privileges"}, to = {"privileges"})
  public Set<AccessData> privDTOToEntity(Set<PrivilegeDTO> privilegeDTOs) {
    return privilegeDTOs.stream().map(p -> {
      Privilege priv = new Privilege();
      priv.setId(p.getId());
      priv.setName(p.getName());
      priv.setIcon(p.getIcon());

      AccessData ac = new AccessData();
      ac.setPrivilege(priv);
      ac.setOrder(p.getOrder());

      return ac;
    }).collect(Collectors.toSet());
  }
}
