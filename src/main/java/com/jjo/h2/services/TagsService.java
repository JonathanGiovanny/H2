package com.jjo.h2.services;

import java.util.List;
import org.springframework.data.domain.Pageable;
import com.jjo.h2.dto.TagsDTO;

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
}
