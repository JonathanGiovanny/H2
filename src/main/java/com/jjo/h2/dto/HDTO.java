package com.jjo.h2.dto;

import java.time.LocalDateTime;
import java.util.Set;
import com.googlecode.jmapper.annotations.JMap;
import com.jjo.h2.model.Tags;
import lombok.Data;

@Data
public class HDTO {

  @JMap
  private Long id;

  @JMap
  private String name;

  @JMap
  private String url;

  @JMap
  private String cover;

  @JMap
  private Long clicks;

  @JMap
  private Double score;

  @JMap
  private HTypeDTO type;

  @JMap
  private Set<Tags> tags;

  @JMap
  private LocalDateTime creationDate;

  @JMap
  private String createdBy;

  @JMap
  private LocalDateTime modifiedDate;
}
