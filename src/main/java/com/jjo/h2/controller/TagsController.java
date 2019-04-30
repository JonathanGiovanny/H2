package com.jjo.h2.controller;

import java.net.URI;
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
import com.jjo.h2.dto.TagsDTO;
import com.jjo.h2.services.TagsService;
import com.jjo.h2.utils.Constants;

@RestController
@RequestMapping(Constants.APP_NAME)
public class TagsController {

  @Autowired
  private TagsService tagsService;

  @GetMapping("/tags/{id}")
  public ResponseEntity<TagsDTO> getTag(@PathVariable Integer id) {
    return ResponseEntity.ok(tagsService.getTag(id));
  }

  @GetMapping("/tags")
  public ResponseEntity<?> findAll(Pageable pageable) {
    return ResponseEntity.ok(tagsService.findAll(pageable));
  }

  @PostMapping("/tags")
  public ResponseEntity<?> saveTag(@RequestBody TagsDTO tag) {
    return ResponseEntity.created(URI.create(tagsService.saveTag(tag).getId().toString())).build();
  }

  @PutMapping("/tags/{id}")
  public ResponseEntity<?> updateTag(@PathVariable Integer id, @RequestBody TagsDTO tag) {
    tag.setId(id);
    return ResponseEntity.ok(tagsService.updateTag(tag));
  }

  @DeleteMapping("/tags/{id}")
  public ResponseEntity<?> deleteTag(@PathVariable Integer id) {
    tagsService.deleteTag(id);
    return ResponseEntity.noContent().build();
  }
}
