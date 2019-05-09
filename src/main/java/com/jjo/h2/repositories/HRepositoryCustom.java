package com.jjo.h2.repositories;

import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import com.jjo.h2.model.H;

public interface HRepositoryCustom extends QuerydslPredicateExecutor<H> {

  List<H> filter(H filter, Pageable pageable);
}
