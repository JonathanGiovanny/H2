package com.jjo.h2.exception;

import static com.jjo.h2.exception.ErrorConstants.BAD_QUERY;
import static com.jjo.h2.exception.ErrorConstants.GENERIC_ERROR_MSG;
import static com.jjo.h2.exception.ErrorConstants.INVALID_DATE_FORMAT;
import static com.jjo.h2.exception.ErrorConstants.MISMATCH_FIELD;
import static com.jjo.h2.exception.ErrorConstants.MISMATCH_INPUT;
import static com.jjo.h2.exception.ErrorConstants.MISSING_FIELD;
import static com.jjo.h2.exception.ErrorConstants.NO_DATA_MSG;
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
import com.googlecode.jmapper.exceptions.JMapperException;
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
  protected ResponseEntity<Set<HErrorDTO>> handleHException(final HException exception) {
    return ResponseEntity.badRequest().body(Set.of(exBuilder(exception.getMessage(), exception)));
  }

  /**
   * Handle all the exceptions with a generic message
   * 
   * @param exception
   * @return
   */
  @ExceptionHandler(Exception.class)
  protected ResponseEntity<Set<HErrorDTO>> handleOtherException(final Exception exception) {
    return ResponseEntity.badRequest().body(Set.of(exBuilder(GENERIC_ERROR_MSG, exception)));
  }

  /**
   * Functional exception, generic message
   * 
   * @param exception
   * @return
   */
  @ExceptionHandler(NoSuchElementException.class)
  protected ResponseEntity<Set<HErrorDTO>> handleNoElementException(final Exception exception) {
    return ResponseEntity.badRequest().body(Set.of(exBuilder(NO_DATA_MSG, exception)));
  }

  /**
   * Not data found for a specific value
   * 
   * @param request
   * @param exception
   * @return
   */
  @ExceptionHandler(EntityNotFoundException.class)
  protected ResponseEntity<Set<HErrorDTO>> handleEntityNotFoundException(final HttpServletRequest request,
      final Exception exception) {
    return ResponseEntity.badRequest().body(Set.of(exBuilder(exception.getMessage(), exception)));
  }

  /**
   * Error generated for the mapper
   * 
   * @param request
   * @param exception
   * @return
   * @throws Throwable
   */
  @ExceptionHandler(JMapperException.class)
  protected ResponseEntity<Set<HErrorDTO>> handleMapperException(final HttpServletRequest request,
      final Exception exception) throws Throwable {
    if (exception.getCause() instanceof EntityNotFoundException) {
      EntityNotFoundException entityException = (EntityNotFoundException) exception.getCause();
      return handleEntityNotFoundException(request, entityException);
    }
    return ResponseEntity.badRequest().body(Set.of(exBuilder(GENERIC_ERROR_MSG, exception)));
  }

  /**
   * Wrong signature for the request
   * 
   * @param exception
   * @return
   */
  @ExceptionHandler({MismatchedInputException.class, HttpMessageNotReadableException.class})
  protected ResponseEntity<Set<HErrorDTO>> handleJSONException(final Exception exception) {
    String exMessage = exception.getMessage();
    Pattern p = Pattern.compile("\\[\\\"\\w*\\\"\\]");
    Matcher m = p.matcher(exMessage);
    String userErrorMessage;
    if (m.find()) {
      userErrorMessage = String.format(MISMATCH_FIELD, m.group(m.groupCount()).replace("\"", "'"));
    } else {
      userErrorMessage = MISMATCH_INPUT;
    }
    return ResponseEntity.badRequest().body(Set.of(exBuilder(userErrorMessage, exception)));
  }

  /**
   * Javax validations exception
   * 
   * @param exception
   * @return
   */
  @ExceptionHandler(MethodArgumentNotValidException.class)
  protected ResponseEntity<Set<HErrorDTO>> handleMethodArgumentNotValidException(final Exception exception) {
    MethodArgumentNotValidException methodArgumentNotValidException = (MethodArgumentNotValidException) exception;

    List<ObjectError> l = methodArgumentNotValidException.getBindingResult().getAllErrors();

    Set<HErrorDTO> errors = l.stream().filter(objectError -> objectError instanceof FieldError) //
        .map(objectError -> (FieldError) objectError) //
        .map(fieldError -> HErrorDTO.builder() //
            .eventTime(LocalDateTime.now()) //
            .userMessage(getUserMessage(fieldError)) //
            .techMessage(fieldError.toString()) //
            .build())
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
  protected ResponseEntity<Set<HErrorDTO>> handleDateTimeParseException(final DateTimeParseException exception) {
    return ResponseEntity.badRequest().body(Set.of(exBuilder(INVALID_DATE_FORMAT, exception)));
  }

  @ExceptionHandler({SQLException.class, SQLGrammarException.class, InvalidDataAccessResourceUsageException.class})
  protected ResponseEntity<Set<HErrorDTO>> handleSQLException(final Exception exception) {
    Exception e;

    if (exception instanceof SQLGrammarException) {
      e = (Exception) exception.getCause();
    } else {
      e = exception;
    }

    return ResponseEntity.badRequest().body(Set.of(exBuilder(BAD_QUERY, e)));
  }

  /**
   * Get the user message for the custom validations on the requests
   * 
   * @param fieldError
   * @return
   */
  private String getUserMessage(FieldError fieldError) {
    String msg;

    if (fieldError.getCode().toLowerCase().contains("null")) {
      msg = String.format(MISSING_FIELD, fieldError.getField());
    } else {
      msg = fieldError.getDefaultMessage();
    }

    return msg;
  }

  /**
   * Build the DTO to be send as response
   * 
   * @param userMessage
   * @param e
   * @return
   */
  private HErrorDTO exBuilder(String userMessage, Exception e) {
    log.error(userMessage, e);
    return HErrorDTO.builder() //
        .userMessage(userMessage) //
        .techMessage(e.getMessage()) //
        .eventTime(LocalDateTime.now()) //
        .build();
  }
}
