package com.jjo.h2.exception;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import org.hibernate.exception.SQLGrammarException;
import org.springframework.dao.InvalidDataAccessResourceUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class ExceptionHandlerController {

  /**
   * Handle all the exceptions with a generic message
   * 
   * @param exception
   * @return
   */
  @ExceptionHandler(HException.class)
  protected ResponseEntity<Set<HErrorDTO>> handleHException(final HttpServletRequest request, final HException exception) {
    return ResponseEntity.badRequest()
        .body(Set.of(exBuilder(exception.getUserMessage(), exception.getTechMessage(), request.getRequestURI(), exception)));
  }

  /**
   * Handle all the exceptions with a generic message
   * 
   * @param exception
   * @return
   */
  @ExceptionHandler(Exception.class)
  protected ResponseEntity<Set<HErrorDTO>> handleOtherException(final HttpServletRequest request, final Exception exception) {
    return ResponseEntity.badRequest()
        .body(Set.of(exBuilder(Errors.GENERIC_ERROR.getCode(), Errors.GENERIC_ERROR.getMessage(), request.getRequestURI(), exception)));
  }

  /**
   * Functional exception, generic message
   * 
   * @param exception
   * @return
   */
  @ExceptionHandler(NoSuchElementException.class)
  protected ResponseEntity<Set<HErrorDTO>> handleNoElementException(final HttpServletRequest request, final Exception exception) {
    return ResponseEntity.badRequest()
        .body(Set.of(exBuilder(Errors.NO_DATA.getCode(), Errors.NO_DATA.getMessage(), request.getRequestURI(), exception)));
  }

  /**
   * Not data found for a specific value
   * 
   * @param request
   * @param exception
   * @return
   */
  @ExceptionHandler(EntityNotFoundException.class)
  protected ResponseEntity<Set<HErrorDTO>> handleEntityNotFoundException(final HttpServletRequest request, final Exception exception) {
    return ResponseEntity.badRequest().body(Set.of(exBuilder(exception.getMessage(), exception.getMessage(), request.getRequestURI(), exception)));
  }

  /**
   * Wrong signature for the request
   * 
   * @param exception
   * @return
   */
  @ExceptionHandler({MismatchedInputException.class, HttpMessageNotReadableException.class})
  protected ResponseEntity<Set<HErrorDTO>> handleJSONException(final HttpServletRequest request, final Exception exception) {
    String exMessage = exception.getMessage();
    Pattern p = Pattern.compile("\\[\\\"\\w*\\\"\\]");
    Matcher m = p.matcher(exMessage);
    String userErrorMessage;
    String techErrorMessage;
    if (m.find()) {
      String value = m.group(m.groupCount()).replace("\"", "'");
      userErrorMessage = String.format(Errors.MISMATCH_FIELD.getCode(), value);
      techErrorMessage = String.format(Errors.MISMATCH_FIELD.getMessage(), value);
    } else {
      userErrorMessage = Errors.MISMATCH_INPUT.getCode();
      techErrorMessage = Errors.MISMATCH_INPUT.getMessage();
    }
    return ResponseEntity.badRequest().body(Set.of(exBuilder(userErrorMessage, techErrorMessage, request.getRequestURI(), exception)));
  }

  @ExceptionHandler({ConstraintViolationException.class})
  protected ResponseEntity<Set<HErrorDTO>> handleConstraintViolationException(final HttpServletRequest request, final Exception exception) {
    ConstraintViolationException constraintViolation = (ConstraintViolationException) exception;
    Set<HErrorDTO> errors = constraintViolation.getConstraintViolations().stream().distinct()
        .map(constraint -> {
          final String message = constraint.getPropertyPath().toString() + " " + constraint.getMessage();
          return exBuilder(message, message, request.getRequestURI(), exception);
        })
        .collect(Collectors.toSet());
    return ResponseEntity.badRequest().body(errors);
  }

  /**
   * Spring validations exception
   * 
   * @param exception
   * @return
   */
  @ExceptionHandler(MethodArgumentNotValidException.class)
  protected ResponseEntity<Set<HErrorDTO>> handleMethodArgumentNotValidException(final HttpServletRequest request, final Exception exception) {
    MethodArgumentNotValidException methodArgumentNotValidException = (MethodArgumentNotValidException) exception;

    List<ObjectError> l = methodArgumentNotValidException.getBindingResult().getAllErrors();

    Set<HErrorDTO> errors = l.stream().filter(objectError -> objectError instanceof FieldError) //
        .map(objectError -> (FieldError) objectError) //
        .map(fieldError -> getMessages(fieldError)).map(msgs -> exBuilder(msgs[0], msgs[1], request.getRequestURI(), exception))
        .collect(Collectors.toSet());

    return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(errors);
  }

  /**
   * Date Time exception
   * 
   * @param exception
   * @return
   */
  @ExceptionHandler(DateTimeParseException.class)
  protected ResponseEntity<Set<HErrorDTO>> handleDateTimeParseException(final HttpServletRequest request, final DateTimeParseException exception) {
    return ResponseEntity.badRequest()
        .body(Set.of(exBuilder(Errors.INVALID_DATE_FORMAT.getCode(), Errors.INVALID_DATE_FORMAT.getMessage(), request.getRequestURI(), exception)));
  }

  @ExceptionHandler({SQLException.class, SQLGrammarException.class, InvalidDataAccessResourceUsageException.class})
  protected ResponseEntity<Set<HErrorDTO>> handleSQLException(final HttpServletRequest request, final Exception exception) {
    Exception e;

    if (exception instanceof SQLGrammarException) {
      e = (Exception) exception.getCause();
    } else {
      e = exception;
    }

    return ResponseEntity.badRequest().body(Set.of(exBuilder(Errors.BAD_QUERY.getCode(), Errors.BAD_QUERY.getMessage(), request.getRequestURI(), e)));
  }

  /**
   * Get the user message for the custom validations on the requests
   * 
   * @param fieldError
   * @return
   */
  private String[] getMessages(FieldError fieldError) {
    String userMsg;
    String techMsg;

    if (fieldError.getCode().toLowerCase().contains("null")) {
      userMsg = String.format(Errors.MISSING_FIELD.getCode(), fieldError.getField());
      techMsg = String.format(Errors.MISSING_FIELD.getMessage(), fieldError.getField());
    } else {
      userMsg = fieldError.getDefaultMessage();
      techMsg = fieldError.toString();
    }

    return new String[] {userMsg, techMsg};
  }

  /**
   * Build the DTO to be send as response
   * 
   * @param userMessage
   * @param techMessage
   * @param path
   * @param e
   * @return
   */
  private HErrorDTO exBuilder(String userMessage, String techMessage, String path, Exception e) {
    log.error(userMessage, e);
    return HErrorDTO.builder() //
        .userMessage(userMessage) //
        .techMessage(techMessage) //
        .timestamp(LocalDateTime.now()) //
        .path(path) //
        .build();
  }
}
