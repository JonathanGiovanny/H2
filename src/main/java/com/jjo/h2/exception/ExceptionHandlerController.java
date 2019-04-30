package com.jjo.h2.exception;

import static com.jjo.h2.exception.ErrorConstants.GENERIC_ERROR_MSG;
import java.time.LocalDateTime;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerController {

  @ExceptionHandler(Exception.class)
  protected ResponseEntity<HExceptionDTO> handleOtherException(Exception e) {
    HExceptionDTO hDTO = HExceptionDTO.builder().userMessage(GENERIC_ERROR_MSG).techMessage(e.getMessage())
        .eventTime(LocalDateTime.now()).build();
    return ResponseEntity.badRequest().body(hDTO);
  }
}
