package com.jjo.h2.exception;

import static com.jjo.h2.exception.ErrorConstants.GENERIC_ERROR_MSG;
import static com.jjo.h2.exception.ErrorConstants.MISMATCH_FIELD;
import static com.jjo.h2.exception.ErrorConstants.MISMATCH_INPUT;
import static com.jjo.h2.exception.ErrorConstants.NO_DATA_MSG;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;

@ControllerAdvice
public class ExceptionHandlerController {

  @ExceptionHandler(Exception.class)
  protected ResponseEntity<HExceptionDTO> handleOtherException(Exception e) {
    e.printStackTrace();
    return ResponseEntity.badRequest().body(exBuilder(GENERIC_ERROR_MSG, e));
  }

  @ExceptionHandler(NoSuchElementException.class)
  protected ResponseEntity<HExceptionDTO> handleNoElementException(Exception e) {
    e.printStackTrace();
    return ResponseEntity.badRequest().body(exBuilder(NO_DATA_MSG, e));
  }

  @ExceptionHandler({MismatchedInputException.class, HttpMessageNotReadableException.class})
  protected ResponseEntity<HExceptionDTO> handleJSONException(Exception e) {
    String exMessage = e.getMessage();
    Pattern p = Pattern.compile("\\[\\\"\\w*\\\"\\]");
    Matcher m = p.matcher(exMessage);
    String userErrorMessage;
    if (m.find()) {
      userErrorMessage = m.group(m.groupCount()).replace("\"", "'") + MISMATCH_FIELD;
    } else {
      userErrorMessage = MISMATCH_INPUT;
    }
    e.printStackTrace();
    return ResponseEntity.badRequest().body(exBuilder(userErrorMessage, e));
  }

  /**
   * Build the DTO to be send as response
   * 
   * @param userMessage
   * @param e
   * @return
   */
  private HExceptionDTO exBuilder(String userMessage, Exception e) {
    return HExceptionDTO.builder() //
        .userMessage(userMessage) //
        .techMessage(e.getMessage()) //
        .eventTime(LocalDateTime.now()) //
        .build();
  }
}
