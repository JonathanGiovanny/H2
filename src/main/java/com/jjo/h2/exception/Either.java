package com.jjo.h2.exception;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@ToString
public class Either<L extends RuntimeException, R> {

  private final L left;
  private final Optional<R> right;

  public static <L extends RuntimeException, R> Either<L, R> of(L left, Optional<R> right) {
    return new Either<>(left, right);
  }

  public Optional<L> getLeft() {
    return Optional.ofNullable(left);
  }

  public Optional<R> getRight() {
    return right;
  }

  public boolean isLeft() {
    return left != null && !isRight();
  }

  public boolean isRight() {
    return right != null && right.isPresent();
  }

  public <T> Optional<T> mapLeft(Function<? super L, T> mapper) {
    if (isLeft()) {
      return Optional.of(mapper.apply(left));
    }
    return Optional.empty();
  }

  public <T> Optional<T> mapRight(Function<? super R, T> mapper) {
    if (isRight()) {
      return right.map(mapper);
    }
    return Optional.empty();
  }

  public void ifPresent(Consumer<? super R> action) {
    if (isRight()) {
      action.accept(right.get());
    }
  }

  public void ifPresentOrThrow(Consumer<? super R> action) {
    if (!isRight()) {
      throw left;
    }
    action.accept(right.get());
  }

  public R getOrElse() {
    if (isLeft() || getRight().isEmpty()) {
      throw left;
    }
    return right.get();
  }
}
