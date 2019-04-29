package com.jjo.h2.services;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.jjo.h2.dto.HTypeDTO;
import com.jjo.h2.model.HType;
import com.jjo.h2.repositories.HTypeRepository;
import com.jjo.h2.utils.MapperUtil;

@Service
public class HTypeServiceImpl implements HTypeService {

  @Autowired
  private HTypeRepository hTypeRepo;

  @Autowired
  private MapperUtil mapperUtil;

  @Override
  public HTypeDTO getHType(Integer id) {
    return toDTO(hTypeRepo.getOne(id));
  }

  @Override
  public List<HTypeDTO> findAll(Pageable pageable) {
    return hTypeRepo.findAll(pageable).getContent().stream().map(ht -> toDTO(ht))
        .collect(Collectors.toList());
  }

  @Override
  public boolean saveHType(HTypeDTO hType) {
    return hTypeRepo.save(toEntity(hType)) != null;
  }

  @Override
  public boolean updateHType(Integer id, HTypeDTO hType) {
    HType hTypeEntity = hTypeRepo.getOne(id);
    hTypeEntity.setName(hType.getName());
    return hTypeRepo.save(hTypeEntity) != null;
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
  private HType toEntity(HTypeDTO dto) {
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
