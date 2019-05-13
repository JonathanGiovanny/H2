package com.jjo.h2.repositories.impl;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import com.jjo.h2.model.H;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;

public abstract class DSLQueryCustomImpl {

  @Override
  public Optional<H> findOne(Predicate predicate) {
    return null;
  }

  @Override
  public Iterable<H> findAll(Predicate predicate) {
    return null;
  }

  @Override
  public Iterable<H> findAll(Predicate predicate, Sort sort) {
    return null;
  }

  @Override
  public Iterable<H> findAll(Predicate predicate, OrderSpecifier<?>... orders) {
    return null;
  }

  @Override
  public Iterable<H> findAll(OrderSpecifier<?>... orders) {
    return null;
  }

  @Override
  public Page<H> findAll(Predicate predicate, Pageable pageable) {
    return null;
  }

  @Override
  public long count(Predicate predicate) {
    return 0;
  }

  @Override
  public boolean exists(Predicate predicate) {
    return false;
  }
}
