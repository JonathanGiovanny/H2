package com.jjo.h2.controller;

import java.net.URI;
import java.util.List;
import javax.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.jjo.h2.controller.validator.HDTOValidator;
import com.jjo.h2.dto.HDTO;
import com.jjo.h2.services.HService;
import com.jjo.h2.utils.Constants;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping(Constants.APP_NAME + "/h")
public class HController {

  private final @NonNull HService hService;

  private final HDTOValidator hValid;

  @InitBinder
  protected void initBinder(WebDataBinder binder) {
    binder.addValidators(hValid);
  }

  @GetMapping("/{id}")
  public ResponseEntity<HDTO> getH(@PathVariable Long id) {
    return ResponseEntity.ok(hService.getH(id));
  }

  @GetMapping
  public ResponseEntity<List<HDTO>> findAll(Pageable pageable) {
    return ResponseEntity.ok(hService.findAll(pageable));
  }

  @PostMapping("/search")
  public ResponseEntity<List<HDTO>> searchH(@RequestBody HDTO h, Pageable pageable) {
    return ResponseEntity.ok(hService.findAll(h, pageable));
  }

  @PostMapping
  public ResponseEntity<Void> saveH(@Valid @RequestBody HDTO h) {
    return ResponseEntity.created(URI.create(hService.saveH(h).getId().toString())).build();
  }

  @PutMapping("/{id}")
  public ResponseEntity<HDTO> saveH(@PathVariable Long id, @Valid @RequestBody HDTO h) {
    return ResponseEntity.ok(hService.updateH(id, h));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteH(@PathVariable Long id) {
    hService.deleteH(id);
    return ResponseEntity.noContent().build();
  }

  @PatchMapping("/{id}/click")
  public ResponseEntity<HDTO> increaseClick(@PathVariable Long id) {
    return ResponseEntity.ok(hService.increaseClick(id));
  }
}
