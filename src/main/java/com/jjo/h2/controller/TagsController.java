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
import com.jjo.h2.dto.TagsDTO;
import com.jjo.h2.services.TagsService;
import com.jjo.h2.utils.Constants;

@RestController
@RequestMapping(Constants.APP_NAME + "/tags")
public class TagsController {

  @Autowired
  private TagsService tagsService;

  @GetMapping("/{id}")
  public ResponseEntity<TagsDTO> getTag(@PathVariable Long id) {
    return ResponseEntity.ok(tagsService.getTag(id));
  }

  @GetMapping
  public ResponseEntity<List<TagsDTO>> findAll(Pageable pageable) {
    return ResponseEntity.ok(tagsService.findAll(pageable));
  }

  @PostMapping
  public ResponseEntity<Void> saveTag(@RequestBody TagsDTO tag) {
    return ResponseEntity.created(URI.create(tagsService.saveTag(tag).getId().toString())).build();
  }

  @PutMapping("/{id}")
  public ResponseEntity<TagsDTO> updateTag(@PathVariable Long id, @RequestBody TagsDTO tag) {
    return ResponseEntity.ok(tagsService.updateTag(id, tag));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteTag(@PathVariable Long id) {
    tagsService.deleteTag(id);
    return ResponseEntity.noContent().build();
  }
}
