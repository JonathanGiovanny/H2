package com.jjo.h2.controller.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import com.jjo.h2.dto.TagsDTO;
import com.jjo.h2.services.TagsService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class TagsValidator implements Validator {

  private final @NonNull TagsService tagsService;

  @Override
  public boolean supports(Class<?> clazz) {
    return TagsDTO.class.isAssignableFrom(clazz);
  }

  @Override
  public void validate(Object target, Errors errors) {}
}
