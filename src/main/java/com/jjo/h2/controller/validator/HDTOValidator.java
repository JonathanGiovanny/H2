package com.jjo.h2.controller.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import com.jjo.h2.dto.HDTO;

@Component
public class HDTOValidator implements Validator {

  @Override
  public boolean supports(Class<?> clazz) {
    return HDTO.class.isAssignableFrom(clazz);
  }

  @Override
  public void validate(Object target, Errors errors) {
    HDTO dto = (HDTO) target;

    // ValidationUtils.rejectIfEmptyOrWhitespace(errors, "url", "", "URL is a required field");
    // ValidationUtils.rejectIfEmptyOrWhitespace(errors, "url", "url.required");
  }
}
