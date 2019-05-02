package com.jjo.h2.controller.validator;

import static com.jjo.h2.exception.ErrorConstants.MISSING_FIELD;
import java.util.Objects;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import com.jjo.h2.dto.HDTO;
import com.jjo.h2.dto.HTypeDTO;
import com.jjo.h2.utils.Utils;

@Component
public class HDTOValidator implements Validator {

  @Override
  public boolean supports(Class<?> clazz) {
    return false;
  }

  @Override
  public void validate(Object target, Errors errors) {
    HDTO dto = (HDTO) target;

    ValidationUtils.rejectIfEmptyOrWhitespace(errors, "url", "url.required");

    if (Objects.isNull(dto.getType()))
      errors.rejectValue("type", "type.required", "type" + MISSING_FIELD);

    if (Utils.IS_NUMBER.test(dto.getType())) {
    }
    // dto.setType(type);

    if (!(dto.getType() instanceof HTypeDTO)) {
    }
  }
}
