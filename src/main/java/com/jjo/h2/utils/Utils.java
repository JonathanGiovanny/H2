package com.jjo.h2.utils;

import java.util.Optional;

public class Utils {

  /**
   * Private constructor for static only methods
   */
  private Utils() {
    super();
  }

  /**
   * Validates whenever a value is null or empty for a String
   */
  public static String isNotNullOr(String dto, String entity) {
    return Optional.ofNullable(dto).filter(s -> !s.isEmpty()).orElse(entity);
  }

  /**
   * Validates whenever a value is null for Long
   */
  public static <T extends Object> T isNotNullOr(T dto, T entity) {
    return Optional.ofNullable(dto).orElse(entity);
  }
}
