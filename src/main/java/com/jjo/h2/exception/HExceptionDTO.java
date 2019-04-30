package com.jjo.h2.exception;

import java.time.LocalDateTime;
import com.googlecode.jmapper.annotations.JMap;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class HExceptionDTO {

  @JMap
  private Long id;
  @JMap
  private String userMessage;
  @JMap
  private String techMessage;
  @JMap
  private LocalDateTime eventTime;
}
