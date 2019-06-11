package com.jjo.h2.controller.security;

import java.net.URI;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.jjo.h2.config.security.SecurityConstants;
import com.jjo.h2.dto.security.PrivilegeDTO;
import com.jjo.h2.services.security.PrivilegeService;

@RestController
@RequestMapping(SecurityConstants.SECURITY_PATH + "/privileges")
public class PrivilegeController {

  @Autowired
  private PrivilegeService privilegeService;

  @GetMapping
  public ResponseEntity<List<PrivilegeDTO>> getRoles(Pageable pageable) {
    return ResponseEntity.ok(privilegeService.findAll(pageable));
  }

  @PostMapping
  public ResponseEntity<Void> saveRole(@RequestBody PrivilegeDTO privilege) {
    return ResponseEntity.created(URI.create(privilegeService.savePrivilege(privilege).getId().toString())).build();
  }

  @PutMapping("/{id}")
  public ResponseEntity<PrivilegeDTO> updateRole(@PathVariable Long id, @RequestBody PrivilegeDTO privilege) {
    return ResponseEntity.ok(privilegeService.updatePrivilege(id, privilege));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> updateRole(@PathVariable Long id) {
    privilegeService.deletePrivilege(id);
    return ResponseEntity.noContent().build();
  }
}
