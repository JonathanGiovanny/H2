package com.jjo.h2.repositories;

import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.jjo.h2.model.Tags;

public interface TagsRepository extends JpaRepository<Tags, Long> {

  @Query(value = "SELECT t FROM Tags t WHERE LOWER(t.name) = LOWER(:name)")
  Tags findByName(@Param(value = "name") String name);
  
  @Query(value = "SELECT t FROM Tags t WHERE LOWER(t.name) LIKE LOWER(:name)")
  List<Tags> findByNameLike(@Param(value = "name") String name, Pageable pageable);
}
