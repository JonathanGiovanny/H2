package com.jjo.h2.repositories.impl;

import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import com.jjo.h2.model.H;
import com.jjo.h2.model.QH;
import com.jjo.h2.repositories.HRepositoryCustom;
import com.jjo.h2.utils.Utils;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQuery;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class HRepositoryCustomImpl implements HRepositoryCustom {

  private final @NonNull EntityManager em;
  
  @Override
  public List<H> filter(H hFilter, Pageable pageable) {
    JPAQuery<H> query = new JPAQuery<>(em);
    QH h = QH.h;

    query.from(h);

    Optional.ofNullable(hFilter.getName()).ifPresent(name -> query.where(h.name.containsIgnoreCase(name)));
    Optional.ofNullable(hFilter.getUrl()).ifPresent(url -> query.where(h.url.containsIgnoreCase(url)));
    Optional.ofNullable(hFilter.getCover()).ifPresent(cover -> query.where(h.cover.containsIgnoreCase(cover)));
    Optional.ofNullable(hFilter.getType()).ifPresent(type -> query.where(h.type.id.eq(type.getId())));
    Utils.isNotNullOrEmpty(hFilter.getTags()).forEach(tags -> query.where(h.tags.any().id.eq(tags.getId())));

    Optional.of(pageable).filter(Pageable::isPaged).ifPresent(p -> {
      query.limit(p.getPageSize() * (p.getPageNumber() + 1));
      query.offset(p.getOffset());
    });

    return query.fetch();
  }

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
