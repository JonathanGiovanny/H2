package com.jjo.h2.dto;

import com.googlecode.jmapper.annotations.JMap;
import lombok.Data;

@Data
public class HTypeDTO {

  @JMap
  private Integer id;

  @JMap
  private String name;
}
