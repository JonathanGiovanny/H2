package com.jjo.h2.services.security;

import java.util.List;
import com.jjo.h2.dto.security.RoleDTO;

public interface RolesService {

  /**
   * Get the role by its id
   * 
   * @param id
   * @return
   */
  RoleDTO getRoleById(Long id);

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
   * @param id
   * @param role
   * @return
   */
  RoleDTO updateRole(Long id, RoleDTO role);

  /**
   * Update the list of privileges for a Role
   * 
   * @param id
   * @param role
   * @return
   */
  RoleDTO updateRolePrivileges(Long id, RoleDTO role);

  /**
   * Delete the role with the id
   * 
   * @param id
   */
  void deleteRole(Long id);
}
