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

import com.jjo.h2.dto.HDTO;
import com.jjo.h2.dto.util.BaseDTO;
import com.jjo.h2.services.HService;
import com.jjo.h2.utils.Constants;
import com.jjo.h2.utils.ExceptionUtils;
import com.jjo.h2.utils.HException;

@RestController
@RequestMapping(Constants.APP_NAME)
public class HController {

	@Autowired
	private HService hService;

	@GetMapping("/h/{id}")
	public ResponseEntity<? extends BaseDTO> getH(@PathVariable Long id) {
		ResponseEntity<? extends BaseDTO> response = null;
		try {
			response = new ResponseEntity<>(hService.getH(id), HttpStatus.OK);
		} catch (HException e) {
			response = new ResponseEntity<>(ExceptionUtils.getErrorDTO(e), e.getStatusCode());
		}
		return response;
	}

	@GetMapping("/h")
	public ResponseEntity<?> findAll(Pageable pageable) {
		ResponseEntity<?> response = null;
		try {
			response = new ResponseEntity<>(hService.findAll(pageable), HttpStatus.OK);
		} catch (HException e) {
			response = new ResponseEntity<>(ExceptionUtils.getErrorDTO(e), e.getStatusCode());
		}
		return response;
	}

	@PostMapping("/h")
	public ResponseEntity<?> saveH(@RequestBody HDTO h) {
		ResponseEntity<?> response = null;
		try {
			hService.saveH(h);
			response = new ResponseEntity<>(HttpStatus.CREATED);
		} catch (HException e) {
			response = new ResponseEntity<>(ExceptionUtils.getErrorDTO(e), e.getStatusCode());
		}
		return response;
	}

	@PutMapping("/h/{id}")
	public ResponseEntity<?> saveH(@RequestBody Integer id, HDTO h) {
		ResponseEntity<?> response = null;
		try {
			hService.saveH(h);
			response = new ResponseEntity<>(HttpStatus.CREATED);
		} catch (HException e) {
			response = new ResponseEntity<>(ExceptionUtils.getErrorDTO(e), e.getStatusCode());
		}
		return response;
	}

	@DeleteMapping("/h/{id}")
	public ResponseEntity<?> deleteH(@PathVariable Long id) {
		ResponseEntity<?> response = null;
		try {
			hService.deleteH(id);
			response = new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (HException e) {
			response = new ResponseEntity<>(ExceptionUtils.getErrorDTO(e), e.getStatusCode());
		}
		return response;
	}

	@PutMapping("/h/click/{id}")
	public ResponseEntity<?> increaseClick(@PathVariable Long id) {
		ResponseEntity<?> response = null;
		try {
			hService.increaseClick(id);
			response = new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (HException e) {
			response = new ResponseEntity<>(ExceptionUtils.getErrorDTO(e), e.getStatusCode());
		}
		return response;
	}
}
