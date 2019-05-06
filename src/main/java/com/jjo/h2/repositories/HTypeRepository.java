package com.jjo.h2.repositories;

import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.jjo.h2.model.HType;

public interface HTypeRepository extends JpaRepository<HType, Integer> {

  @Query(value = "SELECT ht FROM HType ht WHERE LOWER(ht.name) = LOWER(:name)")
  HType findByName(@Param(value = "name") String name);

  @Query(value = "SELECT ht FROM HType ht WHERE LOWER(ht.name) LIKE LOWER(:name)")
  List<HType> findByNameLike(@Param(value = "name") String name, Pageable pageable);
}
