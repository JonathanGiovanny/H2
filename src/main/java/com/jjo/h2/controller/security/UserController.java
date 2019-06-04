package com.jjo.h2.controller.security;

import java.net.URI;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.jjo.h2.dto.security.SingUpDTO;
import com.jjo.h2.dto.security.UserDTO;
import com.jjo.h2.services.security.UserService;
import com.jjo.h2.utils.Constants;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(Constants.APP_NAME + "/security")
public class UserController {

  private final UserService userService;

  @GetMapping("/singup/checkname/{username}")
  public ResponseEntity<Boolean> checkUsernameOrEmailAvailability(@PathVariable String username) {
    return ResponseEntity.ok(userService.availableUsernameOrEmail(username));
  }
  
  @PostMapping("/singup")
  public ResponseEntity<Void> registerUser(@RequestBody SingUpDTO user) {
    return ResponseEntity.created(URI.create(userService.registerUser(user).toString())).build();
  }

  @GetMapping("/users")
  public ResponseEntity<List<UserDTO>> getUsers(Pageable pageable) {
    return ResponseEntity.ok(userService.getUsers(pageable));
  }

  @PutMapping("/users/{id}")
  public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @RequestBody UserDTO user) {
    return ResponseEntity.ok(userService.updateUser(id, user));
  }
}
