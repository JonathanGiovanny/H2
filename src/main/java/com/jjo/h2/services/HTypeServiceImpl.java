package com.jjo.h2.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.jjo.h2.dto.HTypeDTO;
import com.jjo.h2.exception.ErrorConstants;
import com.jjo.h2.exception.HException;
import com.jjo.h2.model.HType;
import com.jjo.h2.repositories.HTypeRepository;
import com.jjo.h2.utils.MapperUtil;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HTypeServiceImpl implements HTypeService {

  private final @NonNull HTypeRepository hTypeRepo;

  private final @NonNull MapperUtil mapperUtil;

  @Override
  public HTypeDTO getHType(Integer id) {
    return toDTO(hTypeRepo.getOne(id));
  }

  @Override
  public List<HTypeDTO> findAll(Pageable pageable) {
    return hTypeRepo.findAll(pageable).getContent().stream().map(this::toDTO).collect(Collectors.toList());
  }

  @Override
  public HTypeDTO findByName(String name) {
    return toDTO(hTypeRepo.findByName(name));
  }

  @Override
  public List<HTypeDTO> findByNameLike(String name, Pageable pageable) {
    return hTypeRepo.findByNameLike(name, pageable).stream().map(this::toDTO).collect(Collectors.toList());
  }

  @Override
  public Integer saveHType(HTypeDTO hType) {
    if (findByName(hType.getName()) != null) {
      throw new HException(String.format(ErrorConstants.FIELD_SHOULD_UNIQUE, "name"));
    }

    return hTypeRepo.save(toEntity(hType)).getId();
  }

  @Override
  public HTypeDTO updateHType(Integer id, HTypeDTO hType) {
    Optional<HType> hTypeEntity = hTypeRepo.findById(hType.getId());

    HTypeDTO existingType = findByName(hType.getName());
    if (existingType != null && !id.equals(existingType.getId())) {
      throw new HException(String.format(ErrorConstants.FIELD_SHOULD_UNIQUE, "name"));
    }

    HType entity = hTypeEntity.map(ht -> {
      ht.setName(hType.getName());
      return ht;
    }).orElse(toEntity(hType));

    return toDTO(hTypeRepo.save(entity));
  }

  @Override
  public void deleteHType(Integer id) {
    hTypeRepo.deleteById(id);
  }

  /**
   * Call the mapper and transform the object
   * 
   * @param dto
   * @return
   */
  public HType toEntity(HTypeDTO dto) {
    return mapperUtil.getMapper(HType.class, HTypeDTO.class).getDestination(dto);
  }

  /**
   * Call the mapper and transform the object
   * 
   * @param entity
   * @return
   */
  private HTypeDTO toDTO(HType entity) {
    return mapperUtil.getMapper(HTypeDTO.class, HType.class).getDestination(entity);
  }
}
