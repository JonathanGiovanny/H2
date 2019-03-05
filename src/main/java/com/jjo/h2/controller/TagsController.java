package com.jjo.h2.controller;

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

import com.jjo.h2.dto.TagsDTO;
import com.jjo.h2.dto.util.BaseDTO;
import com.jjo.h2.services.TagsService;
import com.jjo.h2.utils.Constants;
import com.jjo.h2.utils.ExceptionUtils;
import com.jjo.h2.utils.HException;

@RestController
@RequestMapping(Constants.APP_NAME)
public class TagsController {

	@Autowired
	private TagsService tagsService;

	@GetMapping("/tags/{id}")
	public ResponseEntity<? extends BaseDTO> getTag(@PathVariable Integer id) {
		ResponseEntity<? extends BaseDTO> response = null;
		try {
			response = new ResponseEntity<>(tagsService.getTag(id), HttpStatus.OK);
		} catch (HException e) {
			response = new ResponseEntity<>(ExceptionUtils.getErrorDTO(e), e.getStatusCode());
		}
		return response;
	}

	@GetMapping("/tags")
	public ResponseEntity<?> findAll(Pageable pageable) {
		ResponseEntity<?> response = null;
		try {
			response = new ResponseEntity<>(tagsService.findAll(pageable), HttpStatus.OK);
		} catch (HException e) {
			response = new ResponseEntity<>(ExceptionUtils.getErrorDTO(e), e.getStatusCode());
		}
		return response;
	}

	@PostMapping("/tags")
	public ResponseEntity<?> saveTag(@RequestBody TagsDTO tag) {
		ResponseEntity<?> response = null;
		try {
			tagsService.saveTag(tag);
			response = new ResponseEntity<>(HttpStatus.CREATED);
		} catch (HException e) {
			response = new ResponseEntity<>(ExceptionUtils.getErrorDTO(e), e.getStatusCode());
		}
		return response;
	}

	@PutMapping("/tags/{id}")
	public ResponseEntity<?> updateTag(@PathVariable Integer id, @RequestBody TagsDTO tag) {
		ResponseEntity<?> response = null;
		try {
			tagsService.updateTag(id, tag);
			response = new ResponseEntity<>(HttpStatus.CREATED);
		} catch (HException e) {
			response = new ResponseEntity<>(ExceptionUtils.getErrorDTO(e), e.getStatusCode());
		}
		return response;
	}

	@DeleteMapping("/tags/{id}")
	public ResponseEntity<?> deleteTag(@PathVariable Integer id) {
		ResponseEntity<?> response = null;
		try {
			tagsService.deleteTag(id);
			response = new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (HException e) {
			response = new ResponseEntity<>(ExceptionUtils.getErrorDTO(e), e.getStatusCode());
		}
		return response;
	}
}
