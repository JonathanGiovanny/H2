package com.jjo.h2.model.security;

import java.util.Arrays;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum StatusEnum {

  ACTIVE("A"), BLOCKED("B"), INACTIVE("I"), CONFLICTED("C");

  @Getter
  private String code;

  /**
   * Get the Status based on the given code
   * 
   * @param code
   * @return
   */
  public static StatusEnum fromCode(String code) {
    return Arrays.stream(StatusEnum.values()).filter(t -> t.code.equals(code))
        .collect(Collectors.reducing((a, b) -> null)).orElse(StatusEnum.CONFLICTED);
  }
}
