package com.jjo.h2.model;

import java.io.Serializable;
import java.util.Set;
import java.util.stream.Collectors;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import com.googlecode.jmapper.annotations.JMap;
import com.googlecode.jmapper.annotations.JMapConversion;
import com.jjo.h2.dto.TagsDTO;
import com.jjo.h2.model.audit.Auditable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "H")
public class H extends Auditable implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 4709089064220157044L;

  @Id
  @JMap
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "H_ID")
  private Long id;

  @JMap
  @Column(name = "H_NAME")
  private String name;

  @JMap
  @Column(name = "H_URL", nullable = false)
  private String url;

  @JMap
  @Column(name = "H_COVER")
  private String cover;

  @JMap
  @Column(name = "H_CLICKS")
  private Long clicks;

  @JMap
  @Column(name = "H_SCORE")
  private Double score;

  @JMap
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "H_TYPE__HT_ID")
  private HType type;

  @JMap
  @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
  @JoinTable(name = "H_TAGS",
      joinColumns = @JoinColumn(name = "H_ID", referencedColumnName = "H_ID"),
      inverseJoinColumns = @JoinColumn(name = "TAG_ID", referencedColumnName = "TAG_ID"))
  private Set<Tags> tags;

//  @JMapConversion(from = {"tags"}, to = {"tags"})
//  public Set<TagsDTO> hEntityToDTO(Set<Tags> hEntity) {
//    return hEntity.stream().map(t -> {
//      TagsDTO tags = new TagsDTO();
//      tags.setId(t.getId());
//      tags.setName(t.getName());
//      return tags;
//    }).collect(Collectors.toSet());
//  }
}
