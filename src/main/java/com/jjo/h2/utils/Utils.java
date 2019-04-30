package com.jjo.h2.utils;

import java.util.Optional;
import java.util.function.BiPredicate;
import java.util.function.Function;

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
   * Validates whenever a value is null
   */
  public static <T extends Object> T isNotNullOr(T dto, T entity) {
    return Optional.ofNullable(dto).orElse(entity);
  }

  /**
   * Validates whenever a value is null, only if the condition is met
   */
  public static <T extends Object> T isNotNullOr(T dto, T entity, BiPredicate<T, T> condition) {
    return Optional.ofNullable(dto).filter(f -> condition.test(dto, entity)).orElse(entity);
  }

  /**
   * Validates whenever a value is null, only if the condition is met, and run a process if that condition is met
   */
  public static <T extends Object> T isNotNullOr(T dto, T entity, BiPredicate<T, T> condition, Function<T, T> process) {
    return Optional.ofNullable(dto).filter(f -> condition.test(dto, entity)).map(process).orElse(entity);
  }
}
