package com.jjo.h2.controller;

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
import com.jjo.h2.dto.HDTO;
import com.jjo.h2.services.HService;
import com.jjo.h2.utils.Constants;

@RestController
@RequestMapping(Constants.APP_NAME)
public class HController {

  @Autowired
  private HService hService;

  @GetMapping("/h/{id}")
  public ResponseEntity<HDTO> getH(@PathVariable Long id) {
    return ResponseEntity.ok(hService.getH(id));
  }

  @GetMapping("/h")
  public ResponseEntity<List<HDTO>> findAll(Pageable pageable) {
    return ResponseEntity.ok(hService.findAll(pageable));
  }

  @PostMapping("/h")
  public ResponseEntity<String> saveH(@RequestBody HDTO h) {
    return ResponseEntity.created(URI.create(hService.saveH(h).getId().toString())).build();
  }

  @PutMapping("/h/{id}")
  public ResponseEntity<HDTO> saveH(@RequestBody Integer id, HDTO h) {
    return ResponseEntity.ok(hService.saveH(h));
  }

  @DeleteMapping("/h/{id}")
  public ResponseEntity<Void> deleteH(@PathVariable Long id) {
    hService.deleteH(id);
    return ResponseEntity.noContent().build();
  }

  @PutMapping("/h/{id}/clicks")
  public ResponseEntity<HDTO> increaseClick(@PathVariable Long id) {
    return ResponseEntity.ok(hService.increaseClick(id));
  }
}
