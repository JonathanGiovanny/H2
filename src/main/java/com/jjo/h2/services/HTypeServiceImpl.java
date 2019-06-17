package com.jjo.h2.services;

import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.jjo.h2.dto.HTypeDTO;
import com.jjo.h2.exception.Errors;
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
  public HTypeDTO saveHType(HTypeDTO hType) {
    HTypeDTO existingName = findByName(hType.getName());
    Predicate<HTypeDTO> hasExistingValue = ht -> Objects.nonNull(existingName) && Objects.isNull(ht.getId());
    Predicate<HTypeDTO> hasExistingValueAndOtherId =
        ht -> Objects.nonNull(existingName) && Objects.nonNull(ht.getId()) && !existingName.getId().equals(ht.getId());

    if (hasExistingValue.or(hasExistingValueAndOtherId).test(hType)) {
      throw new HException(Errors.FIELD_SHOULD_UNIQUE, "name");
    }

    return toDTO(hTypeRepo.save(toEntity(hType)));
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
  @Override
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
