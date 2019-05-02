package com.jjo.h2.controller.security;

import java.net.URI;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.jjo.h2.dto.security.RoleDTO;
import com.jjo.h2.services.security.RolesService;
import com.jjo.h2.utils.Constants;

@RestController
@RequestMapping(Constants.APP_NAME + "/security")
public class RolesController {

  @Autowired
  private RolesService rolesService;

  @GetMapping("/roles")
  public ResponseEntity<List<RoleDTO>> getRoles() {
    return ResponseEntity.ok(rolesService.getRoles());
  }

  @PostMapping("/roles")
  public ResponseEntity<String> saveRole(@RequestBody RoleDTO role) {
    return ResponseEntity.created(URI.create(rolesService.createRole(role).toString())).build();
  }

  @PutMapping("/roles/{id}")
  public ResponseEntity<RoleDTO> updateRole(@PathVariable Long id, @RequestBody RoleDTO role) {
    role.setId(id);
    return ResponseEntity.ok(rolesService.updateRole(role));
  }
  
  @PatchMapping("/roles/{id}/privileges")
  public ResponseEntity<RoleDTO> updateRolePriv(@PathVariable Long id, @RequestBody RoleDTO role) {
    role.setId(id);
    return ResponseEntity.ok(rolesService.updateRolePrivileges(role));
  }

  @DeleteMapping("/roles/{id}")
  public ResponseEntity<Void> updateRole(@PathVariable Long id) {
    rolesService.deleteRole(id);
    return ResponseEntity.noContent().build();
  }
}
