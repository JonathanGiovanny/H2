package com.jjo.h2.services.security;

import java.util.List;
import com.jjo.h2.dto.security.RoleDTO;

public interface RolesService {

  /**
   * Get all roles (not paginated because it is not intended to have many)
   * 
   * @return
   */
  public List<RoleDTO> getRoles();

  /**
   * Create a role
   * 
   * @param role
   * @return
   */
  public Long createRole(RoleDTO role);

  /**
   * Update the role with the id
   * 
   * @param role
   * @return
   */
  public RoleDTO updateRole(RoleDTO role);

  /**
   * Delete the role with the id
   * 
   * @param id
   */
  public void deleteRole(Long id);
}
