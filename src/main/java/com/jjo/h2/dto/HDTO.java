package com.jjo.h2.dto;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;
import com.googlecode.jmapper.annotations.JMap;
import com.googlecode.jmapper.annotations.JMapConversion;
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

//  @JMapConversion(from = {"tags"}, to = {"tags"})
//  public Set<Tags> hDTOToEntity(Set<TagsDTO> tagsEntity) {
//    return tagsEntity.stream().map(t -> {
//      Tags tags = new Tags();
//      tags.setId(t.getId());
//      tags.setName(t.getName());
//      return tags;
//    }).collect(Collectors.toSet());
//  }
}
