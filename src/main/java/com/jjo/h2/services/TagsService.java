package com.jjo.h2.services;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.jjo.h2.dto.TagsDTO;
import com.jjo.h2.exception.HException;

public interface TagsService {

	/**
	 * Query the Tags table to get one value per Id
	 * 
	 * @param id
	 * @return
	 */
	public TagsDTO getTag(Integer id) throws HException;
	
	/**
	 * Get all the tags
	 * 
	 * @param pageable
	 * @return
	 */
	public List<TagsDTO> findAll(Pageable pageable) throws HException;

	/**
	 * Save the Tag
	 * 
	 * @param tag
	 * @return
	 */
	public boolean saveTag(TagsDTO tag) throws HException;

	/**
	 * Update the Tag
	 * @param id
	 * @param tagDto
	 * @return
	 * @throws HException
	 */
	public boolean updateTag(Integer id, TagsDTO tagDto) throws HException;

	/**
	 * Delete one tag
	 * 
	 * @param id
	 */
	public void deleteTag(Integer id) throws HException;
}
