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
import com.jjo.h2.dto.security.PrivilegeDTO;
import com.jjo.h2.services.security.PrivilegeService;
import com.jjo.h2.utils.Constants;

@RestController
@RequestMapping(Constants.APP_NAME + "/security")
public class PrivilegeController {

  @Autowired
  private PrivilegeService privilegeService;

  @GetMapping("/privileges")
  public ResponseEntity<List<PrivilegeDTO>> getRoles(Pageable pageable) {
    return ResponseEntity.ok(privilegeService.findAll(pageable));
  }

  @PostMapping("/privileges")
  public ResponseEntity<String> saveRole(@RequestBody PrivilegeDTO privilege) {
    return ResponseEntity.created(URI.create(privilegeService.savePrivilege(privilege).getId().toString())).build();
  }

  @PutMapping("/privileges/{id}")
  public ResponseEntity<PrivilegeDTO> updateRole(@PathVariable Long id, @RequestBody PrivilegeDTO privilege) {
    privilege.setId(id);
    return ResponseEntity.ok(privilegeService.updatePrivilege(privilege));
  }

  @DeleteMapping("/privileges/{id}")
  public ResponseEntity<Void> updateRole(@PathVariable Long id) {
    privilegeService.deletePrivilege(id);
    return ResponseEntity.noContent().build();
  }
}
