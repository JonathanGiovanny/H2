package com.jjo.h2.utils;

import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Utils {

  public static final Predicate<Object> IS_NUMBER = n -> n instanceof Long || n instanceof Integer;

  /**
   * Validates whenever a value is null or empty for a String
   */
  public static <T> Stream<T> isNotNullOrEmpty(Collection<T> coll) {
    return Optional.ofNullable(coll).map(c -> c.stream()).orElse(Stream.empty()).filter(Objects::nonNull);
  }

  /**
   * Validates whenever a value is null or empty for a String
   */
  public static <T extends Collection<?>> boolean isNullOrEmpty(Collection<?> coll) {
    return Optional.ofNullable(coll).filter(c -> !c.isEmpty()).isEmpty();
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
    return Optional.ofNullable(dto).filter(f -> condition.test(f, entity)).orElse(entity);
  }

  /**
   * Validates whenever a value is null, only if the condition is met, and run a process if that condition is met
   */
  public static <T extends Object> T isNotNullOr(T dto, T entity, BiPredicate<T, T> condition, Function<T, T> process) {
    return Optional.ofNullable(dto).filter(f -> condition.test(dto, entity)).map(process).orElse(entity);
  }

  /**
   * Validates whenever a value is null, only if the condition is met, and run a process if that condition is met
   */
  public static <T extends Object, R extends Object> R isNotNullROr(T dto, R entity, BiPredicate<T, R> condition,
      Function<T, R> process) {
    return Optional.ofNullable(dto).filter(f -> condition.test(dto, entity)).map(process).orElse(entity);
  }
}
