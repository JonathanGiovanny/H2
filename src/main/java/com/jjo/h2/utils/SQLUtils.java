package com.jjo.h2.utils;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.UnaryOperator;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SQLUtils {

  /**
   * Since returning null is not valid, this will return the array that will contains the javax Predicate required
   * 
   * @param predicates
   * @return
   */
  @SafeVarargs
  public static Predicate[] unifyPredicate(Optional<Predicate>... predicates) {
    return Arrays.asList(predicates).stream().filter(p -> p.isPresent()).map(Optional::get).toArray(Predicate[]::new);
  }

  /**
   * SQL Utility to generate a common validation
   * 
   * @param <T>
   * @param value
   * @param sqlValidation
   * @return
   */
  public static <T extends Object> Optional<Predicate> sql(T value, Function<Object, Predicate> sqlValidation) {
    return Optional.ofNullable(value).filter(Objects::nonNull).map(sqlValidation);
  }

  /**
   * SQL Utility to generate a common validation applying a custom additional filter
   * 
   * @param <T>
   * @param value
   * @param sqlValidation
   * @param condition
   * @return
   */
  public static <T> Optional<Predicate> sql(T value, Function<T, Predicate> sqlValidation,
      java.util.function.Predicate<T> condition) {
    return Optional.ofNullable(value).filter(Objects::nonNull).filter(condition).map(sqlValidation);
  }

  /**
   * SQL Utility to generate a common validation applying a custom processing to the data
   * 
   * @param <T>
   * @param <R>
   * @param value
   * @param sqlValidation
   * @param condition
   * @param process
   * @return
   */
  public static <T, R> Optional<Predicate> sql(T value, Function<R, Predicate> sqlValidation, Function<T, R> process) {
    return Optional.ofNullable(value).filter(Objects::nonNull).map(process).filter(Objects::nonNull).map(sqlValidation);
  }

  /**
   * SQL Utility to generate a common validation applying a custom additional filter and processing the data
   * 
   * @param <T>
   * @param <R>
   * @param value
   * @param sqlValidation
   * @param condition
   * @param process
   * @return
   */
  public static <T> Optional<Predicate> sql(T value, Function<T, Predicate> sqlValidation, UnaryOperator<T> process,
      java.util.function.Predicate<T> condition) {
    return Optional.ofNullable(value).filter(Objects::nonNull).map(process).filter(Objects::nonNull).filter(condition)
        .map(sqlValidation);
  }

  /**
   * SQL Utility to generate a common validation applying a custom additional filter and processing the data to get
   * another dataType
   * 
   * @param <T>
   * @param <R>
   * @param value
   * @param sqlValidation
   * @param condition
   * @param process
   * @return
   */
  public static <T, R> Optional<Predicate> sql(T value, Function<R, Predicate> sqlValidation, Function<T, R> process,
      java.util.function.Predicate<R> condition) {
    return Optional.ofNullable(value).filter(Objects::nonNull).map(process).filter(condition).map(sqlValidation);
  }

  /**
   * SQL Utility to generate a common validation applying a custom additional filter and processing the data
   * 
   * @param <T>
   * @param <R>
   * @param value
   * @param sqlValidation
   * @param condition
   * @param process
   * @return
   */
  public static <T> Optional<Predicate> sql(T value, Function<T, Predicate> sqlValidation,
      java.util.function.Predicate<T> condition, UnaryOperator<T> process) {
    return Optional.ofNullable(value).filter(Objects::nonNull).filter(condition).map(process).filter(Objects::nonNull)
        .map(sqlValidation);
  }

  /**
   * SQL Utility to generate a common validation applying a custom additional filter and processing the data to get
   * another dataType
   * 
   * @param <T>
   * @param <R>
   * @param value
   * @param sqlValidation
   * @param condition
   * @param process
   * @return
   */
  public static <T, R> Optional<Predicate> sql(T value, Function<R, Predicate> sqlValidation,
      java.util.function.Predicate<T> condition, Function<T, R> process) {
    return Optional.ofNullable(value).filter(Objects::nonNull).filter(condition).map(process).map(sqlValidation);
  }

  /**
   * SQL Utility to generate a common like validation
   * 
   * @param <T> Entity
   * @param cb CriteriaBuilder
   * @param root Root
   * @param field
   * @param value
   * @return Predicate from javax.persistence, not java.util.predicate
   */
  public static <T> Optional<Predicate> sqlLike(CriteriaBuilder cb, Root<T> root, String field, String value) {
    return Optional.ofNullable(value).filter(Objects::nonNull)
        .map(v -> cb.like(root.get(field), String.format("%%%s%%", v)));
  }
}
