package com.jjo.h2.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.jjo.h2.dto.TagsDTO;
import com.jjo.h2.model.Tags;
import com.jjo.h2.repositories.TagsRepository;
import com.jjo.h2.utils.HException;
import com.jjo.h2.utils.MapperUtil;

@Service
public class TagsServiceImpl implements TagsService {

	@Autowired
	private TagsRepository tagsRepo;

	@Autowired
	private MapperUtil mapperUtil;

	@Override
	public TagsDTO getTag(Integer id) throws HException {
		return toDTO(tagsRepo.getOne(id));
	}

	@Override
	public List<TagsDTO> findAll(Pageable pageable) throws HException {
		return tagsRepo.findAll(pageable).getContent().stream().map(t -> toDTO(t)).collect(Collectors.toList());
	}

	@Override
	public boolean saveTag(TagsDTO tag) throws HException {
		return tagsRepo.save(toEntity(tag)) != null;
	}

	@Override
	public boolean updateTag(Integer id, TagsDTO tagDto) throws HException {
		Tags tag = tagsRepo.getOne(id);
		tag.setName(tagDto.getName());
		return tagsRepo.save(tag) != null;
	}

	@Override
	public void deleteTag(Integer id) throws HException {
		tagsRepo.deleteById(id);
	}

	/**
	 * Call the mapper and transform the object
	 * 
	 * @param dto
	 * @return
	 */
	private Tags toEntity(TagsDTO dto) {
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
