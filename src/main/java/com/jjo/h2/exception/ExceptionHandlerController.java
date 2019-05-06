package com.jjo.h2.exception;

import static com.jjo.h2.exception.ErrorConstants.GENERIC_ERROR_MSG;
import static com.jjo.h2.exception.ErrorConstants.MISMATCH_FIELD;
import static com.jjo.h2.exception.ErrorConstants.MISMATCH_INPUT;
import static com.jjo.h2.exception.ErrorConstants.NO_DATA_BY_ID_MSG;
import static com.jjo.h2.exception.ErrorConstants.NO_DATA_MSG;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.HandlerMapping;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;

@ControllerAdvice
public class ExceptionHandlerController {

  private static final String ID = "id";
  private static final String USERNAME = "username";

  @ExceptionHandler(Exception.class)
  protected ResponseEntity<Set<HErrorDTO>> handleOtherException(final Exception e) {
    return ResponseEntity.badRequest().body(Set.of(exBuilder(GENERIC_ERROR_MSG, e)));
  }

  @ExceptionHandler(NoSuchElementException.class)
  protected ResponseEntity<Set<HErrorDTO>> handleNoElementException(final Exception e) {
    return ResponseEntity.badRequest().body(Set.of(exBuilder(NO_DATA_MSG, e)));
  }

  @ExceptionHandler(EntityNotFoundException.class)
  protected ResponseEntity<Set<HErrorDTO>> handleEntityNotFoundException(final HttpServletRequest request, final Exception e) {
    String errorMsg;

    @SuppressWarnings("unchecked")
    Map<String, String> m = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);

    if (m.containsKey(ID)) {
      errorMsg = String.format(NO_DATA_BY_ID_MSG, m.get(ID));

    } else if (m.containsKey(USERNAME)) {
      errorMsg = String.format(NO_DATA_BY_ID_MSG, m.get(USERNAME));

    } else {
      errorMsg = NO_DATA_MSG;
    }

    return ResponseEntity.noContent().build();
  }

  @ExceptionHandler({MismatchedInputException.class, HttpMessageNotReadableException.class})
  protected ResponseEntity<Set<HErrorDTO>> handleJSONException(final Exception e) {
    String exMessage = e.getMessage();
    Pattern p = Pattern.compile("\\[\\\"\\w*\\\"\\]");
    Matcher m = p.matcher(exMessage);
    String userErrorMessage;
    if (m.find()) {
      userErrorMessage = String.format(MISMATCH_FIELD, m.group(m.groupCount()).replace("\"", "'"));
    } else {
      userErrorMessage = MISMATCH_INPUT;
    }
    return ResponseEntity.badRequest().body(Set.of(exBuilder(userErrorMessage, e)));
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Set<HErrorDTO>> handleMethodArgumentNotValidException(final Exception exception) {
    MethodArgumentNotValidException methodArgumentNotValidException = (MethodArgumentNotValidException) exception;

    List<ObjectError> l = methodArgumentNotValidException.getBindingResult().getAllErrors();

    Set<HErrorDTO> errors = l.stream().filter(objectError -> objectError instanceof FieldError) //
        .map(objectError -> (FieldError) objectError) //
        .map(fieldError -> HErrorDTO.builder() //
            .eventTime(LocalDateTime.now()) //
            .userMessage(String.format(MISMATCH_FIELD, fieldError.getField())) //
            .techMessage(fieldError.toString()) //
            .build())
        .collect(Collectors.toSet());

    return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(errors);
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
