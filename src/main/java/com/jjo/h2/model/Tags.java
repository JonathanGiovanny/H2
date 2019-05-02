package com.jjo.h2.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import com.googlecode.jmapper.annotations.JMap;
import lombok.Data;

@Data
@Entity
@Table(name = "TAGS")
public class Tags implements Serializable {

  private static final long serialVersionUID = 4948763217698246169L;

  @Id
  @JMap
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "TAG_ID")
  private Long id;

  @JMap
  @Column(name = "TAG_NAME", nullable = false)
  private String name;
}
