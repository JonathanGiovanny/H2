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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.jjo.h2.controller.validator.HTypeValidator;
import com.jjo.h2.dto.HTypeDTO;
import com.jjo.h2.services.HTypeService;
import com.jjo.h2.utils.Constants;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping(Constants.APP_NAME + "/h/types")
public class HTypeController {

  private final @NonNull HTypeService hTypeService;

  private final @NonNull HTypeValidator htValidator;

  @InitBinder
  protected void initBinder(WebDataBinder binder) {
    binder.addValidators(htValidator);
  }

  @GetMapping("/{id}")
  public ResponseEntity<HTypeDTO> getHType(@PathVariable Integer id) {
    return ResponseEntity.ok(hTypeService.getHType(id));
  }

  @GetMapping
  public ResponseEntity<List<HTypeDTO>> findAll(Pageable pageable) {
    return ResponseEntity.ok(hTypeService.findAll(pageable));
  }

  @PostMapping
  public ResponseEntity<Void> saveHType(@Valid @RequestBody HTypeDTO hType) {
    return ResponseEntity.created(URI.create(hTypeService.saveHType(hType).toString())).build();
  }

  @PutMapping("/{id}")
  public ResponseEntity<HTypeDTO> updateHType(@PathVariable Integer id, @Valid @RequestBody HTypeDTO hType) {
    hType.setId(id);
    return ResponseEntity.ok(hTypeService.updateHType(id, hType));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> saveHType(@PathVariable Integer id) {
    hTypeService.deleteHType(id);
    return ResponseEntity.noContent().build();
  }
}
