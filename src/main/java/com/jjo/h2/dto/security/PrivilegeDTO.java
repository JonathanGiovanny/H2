package com.jjo.h2.dto.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

  /**
   * Represents the value that will be used for the preauthorize validation
   */
  @JMap
  private String privilegeId;

  @JsonIgnore
  private Double order;
}
