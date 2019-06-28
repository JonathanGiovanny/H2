package com.jjo.h2.controller;

import static com.jjo.h2.config.security.SecurityConstants.DELETE_H;
import static com.jjo.h2.config.security.SecurityConstants.FILTER_H;
import static com.jjo.h2.config.security.SecurityConstants.MODIFY_H;
import static com.jjo.h2.config.security.SecurityConstants.READ_H;
import java.net.URI;
import java.security.Principal;
import java.util.List;
import javax.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.jjo.h2.controller.validator.HDTOValidator;
import com.jjo.h2.dto.HDTO;
import com.jjo.h2.mapper.HMapper;
import com.jjo.h2.services.HService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/h")
public class HController {

  private final @NonNull HService hService;

  private final @NonNull HDTOValidator hValid;

  private final @NonNull HMapper mapper;
  
  @InitBinder("HDTO")
  protected void initHBinder(WebDataBinder binder) {
    binder.addValidators(hValid);
  }

  @GetMapping
  @PreAuthorize(READ_H)
  public ResponseEntity<List<HDTO>> findAll(Pageable pageable, Principal principal) {
    return ResponseEntity.ok(mapper.entityToDTO(hService.findAll(pageable)));
  }

  @PostMapping("/search")
  @PreAuthorize(FILTER_H)
  public ResponseEntity<List<HDTO>> searchH(@RequestBody HDTO h, Pageable pageable) {
    return ResponseEntity.ok(mapper.entityToDTO(hService.findAll(mapper.dtoToEntity(h), pageable)));
  }

  @PostMapping
  @PreAuthorize(MODIFY_H)
  public ResponseEntity<Void> saveH(@Valid @RequestBody HDTO h) {
    return ResponseEntity.created(URI.create(hService.saveH(mapper.dtoToEntity(h)).getId().toString())).build();
  }

  @PatchMapping("/{id}")
  @PreAuthorize(MODIFY_H)
  public ResponseEntity<HDTO> updateH(@PathVariable Long id, @Valid @RequestBody HDTO h) {
    return ResponseEntity.ok(mapper.entityToDTO(hService.updateH(id, mapper.dtoToEntity(h))));
  }

  @DeleteMapping("/{id}")
  @PreAuthorize(DELETE_H)
  public ResponseEntity<Void> deleteH(@PathVariable Long id) {
    hService.deleteH(id);
    return ResponseEntity.noContent().build();
  }

  @PatchMapping("/{id}/click")
  public ResponseEntity<HDTO> increaseClick(@PathVariable Long id) {
    return ResponseEntity.ok(mapper.entityToDTO(hService.increaseClick(id)));
  }
}
