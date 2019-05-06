package com.jjo.h2.dto;

import java.time.LocalDateTime;
import java.util.Set;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import com.googlecode.jmapper.annotations.JMap;
import lombok.Data;

@Data
public class HDTO {

  @JMap
  private Long id;

  @JMap 
  private String name;

  @JMap
  @NotNull
  private String url;

  @JMap
  private String cover;

  @JMap
  @Null
  private Long clicks;

  @JMap
  private Double score;

  @JMap
  @NotNull
  private HTypeDTO type;

  @JMap
  private Set<TagsDTO> tags;

  @JMap
  @Null
  private LocalDateTime modifiedDate;
}
