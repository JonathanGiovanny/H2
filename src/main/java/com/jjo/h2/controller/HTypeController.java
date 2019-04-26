package com.jjo.h2.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.jjo.h2.dto.HTypeDTO;
import com.jjo.h2.dto.util.BaseDTO;
import com.jjo.h2.services.HTypeService;
import com.jjo.h2.utils.Constants;

@RestController
@RequestMapping(Constants.APP_NAME)
public class HTypeController {

  @Autowired
  private HTypeService hTypeService;

  @GetMapping("/htypes/{id}")
  public ResponseEntity<? extends BaseDTO> getHType(@PathVariable Integer id) {
    return ResponseEntity.ok(hTypeService.getHType(id));
  }

  @GetMapping("/htypes")
  public ResponseEntity<List<HTypeDTO>> findAll(Pageable pageable) {
    return ResponseEntity.ok(hTypeService.findAll(pageable));
  }

  @PostMapping("/htypes")
  public ResponseEntity<?> saveHType(@RequestBody HTypeDTO hType) {
    ResponseEntity<?> response = null;
    hTypeService.saveHType(hType);
    response = new ResponseEntity<>(HttpStatus.CREATED);
    return response;
  }

  @PutMapping("/htypes/{id}")
  public ResponseEntity<?> updateHType(@PathVariable Integer id, @RequestBody HTypeDTO hType) {
    ResponseEntity<?> response = null;
    hTypeService.updateHType(id, hType);
    response = new ResponseEntity<>(HttpStatus.OK);
    return response;
  }

  @DeleteMapping("/htypes/{id}")
  public ResponseEntity<?> saveHType(@PathVariable Integer id) {
    ResponseEntity<?> response = null;
    hTypeService.deleteHType(id);
    response = new ResponseEntity<>(HttpStatus.NO_CONTENT);
    return response;
  }
}
