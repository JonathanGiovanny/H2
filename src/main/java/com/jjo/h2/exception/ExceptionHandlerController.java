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
  protected ResponseEntity<HErrorDTO> handleOtherException(Exception e) {
    return ResponseEntity.badRequest().body(exBuilder(GENERIC_ERROR_MSG, e));
  }

  @ExceptionHandler(NoSuchElementException.class)
  protected ResponseEntity<HErrorDTO> handleNoElementException(Exception e) {
    return ResponseEntity.badRequest().body(exBuilder(NO_DATA_MSG, e));
  }

  @ExceptionHandler({MismatchedInputException.class, HttpMessageNotReadableException.class})
  protected ResponseEntity<HErrorDTO> handleJSONException(Exception e) {
    String exMessage = e.getMessage();
    Pattern p = Pattern.compile("\\[\\\"\\w*\\\"\\]");
    Matcher m = p.matcher(exMessage);
    String userErrorMessage;
    if (m.find()) {
      userErrorMessage = String.format(MISMATCH_FIELD, m.group(m.groupCount()).replace("\"", "'"));
    } else {
      userErrorMessage = MISMATCH_INPUT;
    }
    return ResponseEntity.badRequest().body(exBuilder(userErrorMessage, e));
  }

  /**
   * Build the DTO to be send as response
   * 
   * @param userMessage
   * @param e
   * @return
   */
  private HErrorDTO exBuilder(String userMessage, Exception e) {
    e.printStackTrace();
    return HErrorDTO.builder() //
        .userMessage(userMessage) //
        .techMessage(e.getMessage()) //
        .eventTime(LocalDateTime.now()) //
        .build();
  }
}
