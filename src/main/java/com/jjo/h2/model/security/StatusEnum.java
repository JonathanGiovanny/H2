package com.jjo.h2.model.security;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum StatusEnum {

  A("ACTIVE"), B("BLOCKED"), I("INACTIVE"), C("CONFLICTED");

  @Getter
  private String description;
}
