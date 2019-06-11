package com.jjo.h2.dto.security;

import com.googlecode.jmapper.annotations.JMap;
import lombok.Data;

@Data
public class PrivilegeDTO {

  @JMap
  private Long id;

  /**
   * Represents the value that will be used for the preauthorize validation
   */
  @JMap
  private String name;

  @JMap
  private String description;
}
