package com.jjo.h2.controller.security;

import java.net.URI;
import java.util.List;
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
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/security/roles")
public class RolesController {

  private final RolesService rolesService;

  @GetMapping
  public ResponseEntity<List<RoleDTO>> getRoles() {
    return ResponseEntity.ok(rolesService.getRoles());
  }

  @GetMapping("/{id}")
  public ResponseEntity<RoleDTO> getRoles(@PathVariable Long id) {
    return ResponseEntity.ok(rolesService.getRoleById(id));
  }

  @PostMapping
  public ResponseEntity<Void> saveRole(@RequestBody RoleDTO role) {
    return ResponseEntity.created(URI.create(rolesService.createRole(role).toString())).build();
  }

  @PutMapping("/{id}")
  public ResponseEntity<RoleDTO> updateRole(@PathVariable Long id, @RequestBody RoleDTO role) {
    return ResponseEntity.ok(rolesService.updateRole(id, role));
  }

  @PatchMapping("/{id}/privileges")
  public ResponseEntity<RoleDTO> updateRolePriv(@PathVariable Long id, @RequestBody RoleDTO role) {
    return ResponseEntity.ok(rolesService.updateRolePrivileges(id, role));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> updateRole(@PathVariable Long id) {
    rolesService.deleteRole(id);
    return ResponseEntity.noContent().build();
  }
}
