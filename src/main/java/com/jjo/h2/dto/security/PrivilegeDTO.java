package com.jjo.h2.dto.security;

import com.googlecode.jmapper.annotations.JMap;
import lombok.Data;

@Data
public class PrivilegeDTO {

  @JMap
  private Long id;

  @JMap
  private String name;

  @JMap
  private String icon;

  @JMap
  private String privilegeId;

  private String order;
}
