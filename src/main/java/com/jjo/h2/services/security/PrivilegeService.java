package com.jjo.h2.services.security;

import java.util.List;
import org.springframework.data.domain.Pageable;
import com.jjo.h2.dto.security.PrivilegeDTO;

public interface PrivilegeService {

  /**
   * Query the privileges table to get one value per Id
   * 
   * @param id
   * @return
   */
  public PrivilegeDTO getPrivilege(Long id);

  /**
   * Get all the privileges
   * 
   * @param pageable
   * @return
   */
  public List<PrivilegeDTO> findAll(Pageable pageable);

  /**
   * Save the privilege
   * 
   * @param privilege
   * @return
   */
  public PrivilegeDTO savePrivilege(PrivilegeDTO privilege);

  /**
   * Update the privilege
   * 
   * @param privilegeDto
   * @return
   */
  public PrivilegeDTO updatePrivilege(PrivilegeDTO privilegeDto);

  /**
   * Delete one privilege
   * 
   * @param id
   */
  public void deletePrivilege(Long id);
}
