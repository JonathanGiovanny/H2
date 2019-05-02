package com.jjo.h2.services.security;

import java.util.List;
import com.jjo.h2.dto.security.RoleDTO;

public interface RolesService {

  /**
   * Get all roles (not paginated because it is not intended to have many)
   * 
   * @return
   */
  List<RoleDTO> getRoles();

  /**
   * Create a role
   * 
   * @param role
   * @return
   */
  Long createRole(RoleDTO role);

  /**
   * Update the role with the id
   * 
   * @param role
   * @return
   */
  RoleDTO updateRole(RoleDTO role);
  
  /**
   * Update the list of privileges for a Role
   * @param role
   * @return
   */
  RoleDTO updateRolePrivileges(RoleDTO role);

  /**
   * Delete the role with the id
   * 
   * @param id
   */
  void deleteRole(Long id);
}
