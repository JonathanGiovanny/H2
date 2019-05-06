package com.jjo.h2.dto;

import javax.validation.constraints.NotNull;
import com.googlecode.jmapper.annotations.JMap;
import lombok.Data;

@Data
public class HTypeDTO {

  @JMap
  private Integer id;

  @JMap
  @NotNull
  private String name;
}
