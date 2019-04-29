package com.jjo.h2.dto.security;

import java.util.Set;
import com.googlecode.jmapper.annotations.JMap;
import lombok.Data;

@Data
public class RoleDTO {

  @JMap
  private Long id;

  @JMap
  private String name;

  private Set<PrivilegeDTO> privileges;
}
