package com.jjo.h2.services;

import java.util.List;
import org.springframework.data.domain.Pageable;
import com.jjo.h2.dto.TagsDTO;
import com.jjo.h2.model.Tags;

public interface TagsService {

  /**
   * Query the Tags table to get one value per Id
   * 
   * @param id
   * @return
   */
  TagsDTO getTag(Long id);

  /**
   * Get all the tags
   * 
   * @param pageable
   * @return
   */
  List<TagsDTO> findAll(Pageable pageable);

  /**
   * Get the tags with exact name
   * 
   * @param name
   * @return
   */
  TagsDTO findByName(String name);

  /**
   * Get the tags with like name
   * 
   * @param name
   * @param pageable
   * @return
   */
  List<TagsDTO> findByNameLike(String name, Pageable pageable);

  /**
   * Save the Tag
   * 
   * @param tag
   * @return
   */
  TagsDTO saveTag(TagsDTO tag);

  /**
   * Update the Tag
   * 
   * @param id
   * @param tagDto
   * @return
   */
  TagsDTO updateTag(Long id, TagsDTO tagDto);

  /**
   * Delete one tag
   * 
   * @param id
   */
  void deleteTag(Long id);

  /**
   * Public access from DTO to Entity
   * 
   * @param dto
   * @return
   */
  Tags toEntity(TagsDTO dto);
}
