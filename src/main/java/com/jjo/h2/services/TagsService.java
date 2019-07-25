package com.jjo.h2.services;

import java.util.List;
import org.springframework.data.domain.Pageable;
import com.jjo.h2.model.Tags;

public interface TagsService {

  Tags getTag(Long id);

  List<Tags> findAll(Pageable pageable);

  Boolean isNameAvailable(Long id, String name);

  Tags findByName(String name);

  List<Tags> findByNameLike(String name, Pageable pageable);

  Tags saveTag(Tags tag);

  Tags updateTag(Long id, Tags tagDto);

  void deleteTag(Long id);
}
