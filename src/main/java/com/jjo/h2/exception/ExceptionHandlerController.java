package com.jjo.h2.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerController {

  @ExceptionHandler(Exception.class)
  protected ResponseEntity<HExceptionDTO> handleOtherException(Exception e) {
    HExceptionDTO hDTO = HExceptionDTO.builder().techMessage(e.getMessage()).build();
    return ResponseEntity.badRequest().body(hDTO);
  }
}
