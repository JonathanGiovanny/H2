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
  public HTypeDTO getHType(Integer id);

  /**
   * Get all the records determined by the pagination
   * 
   * @param pageable
   * @return
   */
  public List<HTypeDTO> findAll(Pageable pageable);

  /**
   * Save HType
   * 
   * @param hType
   * @return
   */
  public Integer saveHType(HTypeDTO hType);

  /**
   * Update HType
   * 
   * @param hType
   * @return
   */
  public HTypeDTO updateHType(HTypeDTO hType);

  /**
   * Delete the record
   * 
   * @param id
   */
  public void deleteHType(Integer id);

  /**
   * Public access from DTO to Entity
   * 
   * @param dto
   * @return
   */
  public HType toEntity(HTypeDTO dto);
}
