package com.jjo.h2.services;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.jjo.h2.exception.Errors;
import com.jjo.h2.exception.HException;
import com.jjo.h2.model.Tags;
import com.jjo.h2.repositories.TagsRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TagsServiceImpl implements TagsService {

  private final @NonNull TagsRepository tagsRepo;

  @Override
  public Tags getTag(Long id) {
    return tagsRepo.getOne(id);
  }

  @Override
  public List<Tags> findAll(Pageable pageable) {
    return tagsRepo.findAll(pageable).getContent();
  }

  @Override
  public Tags findByName(String name) {
    return tagsRepo.findByName(name);
  }

  @Override
  public List<Tags> findByNameLike(String name, Pageable pageable) {
    return tagsRepo.findByNameLike(name, pageable);
  }

  @Override
  public Tags saveTag(Tags tag) {
    if (findByName(tag.getName()) != null) {
      throw new HException(Errors.FIELD_SHOULD_UNIQUE, "name");
    }

    return tagsRepo.save(tag);
  }

  @Override
  public Tags updateTag(Long id, Tags tagDto) {
    Optional<Tags> optTag = tagsRepo.findById(id);

    Tags existingTag = findByName(tagDto.getName());
    if (existingTag != null && !id.equals(existingTag.getId())) {
      throw new HException(Errors.FIELD_SHOULD_UNIQUE, "name");
    }

    Tags entity = optTag.map(t -> {
      t.setName(tagDto.getName());
      return t;
    }).orElse(tagDto);

    return tagsRepo.save(entity);
  }

  @Override
  public void deleteTag(Long id) {
    tagsRepo.deleteById(id);
  }
}
