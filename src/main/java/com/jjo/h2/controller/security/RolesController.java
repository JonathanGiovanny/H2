package com.jjo.h2.controller.security;

import java.net.URI;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
    rolesService.createRole(role);
    return ResponseEntity.created(URI.create("")).build();
  }
}
