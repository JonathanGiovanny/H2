package com.jjo.h2.services;

import java.util.List;
import java.util.Objects;
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
    return tagsRepo.findByNameIgnoreCase(name).get();
  }

  @Override
  public Boolean isNameAvailable(String name) {
    return Objects.nonNull(findByName(name));
  }

  @Override
  public List<Tags> findByNameLike(String name, Pageable pageable) {
    return tagsRepo.findByNameLike(name, pageable);
  }

  @Override
  public Tags saveTag(Tags tag) {
    return Optional.ofNullable(tag)
        .filter(entity -> validateTagNameUnique(entity.getId(), entity.getName()))
        .map(tagsRepo::save)
        .get();
  }

  @Override
  public Tags updateTag(Long id, Tags tag) {
    return saveTag(tag);
  }

  @Override
  public void deleteTag(Long id) {
    tagsRepo.deleteById(id);
  }

  private boolean validateTagNameUnique(Long id, String name) {
    Optional<Tags> existingH = tagsRepo.findByNameIgnoreCase(name);
    if (existingH.isPresent() && !existingH.get().getId().equals(id)) {
      throw new HException(Errors.FIELD_SHOULD_UNIQUE, "name");
    }
    return true;
  }
}
