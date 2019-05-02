package com.jjo.h2.services;

import java.util.List;
import org.springframework.data.domain.Pageable;
import com.jjo.h2.dto.HTypeDTO;
import com.jjo.h2.model.HType;

public interface HTypeService {

  /**
   * Get HType by Id
   * 
   * @param id
   * @return
   */
  HTypeDTO getHType(Integer id);

  /**
   * Get all the records determined by the pagination
   * 
   * @param pageable
   * @return
   */
  List<HTypeDTO> findAll(Pageable pageable);

  /**
   * Save HType
   * 
   * @param hType
   * @return
   */
  Integer saveHType(HTypeDTO hType);

  /**
   * Update HType
   * 
   * @param hType
   * @return
   */
  HTypeDTO updateHType(HTypeDTO hType);

  /**
   * Delete the record
   * 
   * @param id
   */
  void deleteHType(Integer id);

  /**
   * Public access from DTO to Entity
   * 
   * @param dto
   * @return
   */
  HType toEntity(HTypeDTO dto);
}
