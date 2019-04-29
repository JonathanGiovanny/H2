package com.jjo.h2.dto;

import java.time.LocalDateTime;
import com.googlecode.jmapper.annotations.JMap;
import lombok.Data;

@Data
public class HHistoryDTO {

  @JMap
  private Long id;

  @JMap
  private HDTO h;

  @JMap
  private LocalDateTime date;
}
