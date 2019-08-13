package com.jjo.h2.utils;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;
import com.jjo.h2.exception.Errors;
import com.jjo.h2.exception.HException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Utils {

  /**
   * Validates whenever a value is null or empty for a String
   */
  public static String isNotNullOr(String dto, String entity) {
    return Optional.ofNullable(dto).filter(s -> !s.isEmpty()).orElse(entity);
  }

  /**
   * Validates whenever a value is null or empty for a String
   */
  public static <T> Stream<T> isNotNullOrEmpty(Collection<T> coll) {
    return Optional.ofNullable(coll).map(c -> c.stream()).orElse(Stream.empty()).filter(Objects::nonNull);
  }

  public static RuntimeException throwNotExistingElement(List<Object> fields) {
    return new HException(Errors.NO_DATA, fields.toString());
  }
}
