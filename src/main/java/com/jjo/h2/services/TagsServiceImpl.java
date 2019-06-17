package com.jjo.h2.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.jjo.h2.dto.TagsDTO;
import com.jjo.h2.exception.Errors;
import com.jjo.h2.exception.HException;
import com.jjo.h2.model.Tags;
import com.jjo.h2.repositories.TagsRepository;
import com.jjo.h2.utils.MapperUtil;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TagsServiceImpl implements TagsService {

  private final @NonNull TagsRepository tagsRepo;

  private final @NonNull MapperUtil mapperUtil;

  @Override
  public TagsDTO getTag(Long id) {
    return toDTO(tagsRepo.getOne(id));
  }

  @Override
  public List<TagsDTO> findAll(Pageable pageable) {
    return tagsRepo.findAll(pageable).getContent().stream().map(this::toDTO).collect(Collectors.toList());
  }

  @Override
  public TagsDTO findByName(String name) {
    return toDTO(tagsRepo.findByName(name));
  }

  @Override
  public List<TagsDTO> findByNameLike(String name, Pageable pageable) {
    return tagsRepo.findByNameLike(name, pageable).stream().map(this::toDTO).collect(Collectors.toList());
  }

  @Override
  public TagsDTO saveTag(TagsDTO tag) {
    if (findByName(tag.getName()) != null) {
      throw new HException(Errors.FIELD_SHOULD_UNIQUE, "name");
    }

    return toDTO(tagsRepo.save(toEntity(tag)));
  }

  @Override
  public TagsDTO updateTag(Long id, TagsDTO tagDto) {
    Optional<Tags> optTag = tagsRepo.findById(id);

    TagsDTO existingTag = findByName(tagDto.getName());
    if (existingTag != null && !id.equals(existingTag.getId())) {
      throw new HException(Errors.FIELD_SHOULD_UNIQUE, "name");
    }

    Tags entity = optTag.map(t -> {
      t.setName(tagDto.getName());
      return t;
    }).orElse(toEntity(tagDto));

    return toDTO(tagsRepo.save(entity));
  }

  @Override
  public void deleteTag(Long id) {
    tagsRepo.deleteById(id);
  }

  /**
   * Call the mapper and transform the object
   * 
   * @param dto
   * @return
   */
  public Tags toEntity(TagsDTO dto) {
    return mapperUtil.getMapper(Tags.class, TagsDTO.class).getDestination(dto);
  }

  /**
   * Call the mapper and transform the object
   * 
   * @param entity
   * @return
   */
  private TagsDTO toDTO(Tags entity) {
    return mapperUtil.getMapper(TagsDTO.class, Tags.class).getDestination(entity);
  }
}
