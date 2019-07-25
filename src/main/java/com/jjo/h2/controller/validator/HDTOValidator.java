package com.jjo.h2.controller.validator;

import java.util.Objects;
import java.util.Optional;
import java.util.stream.IntStream;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import com.jjo.h2.dto.HDTO;
import com.jjo.h2.dto.TagsDTO;
import com.jjo.h2.model.Tags;
import com.jjo.h2.services.HService;
import com.jjo.h2.services.HTypeService;
import com.jjo.h2.services.TagsService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class HDTOValidator implements Validator {

  private static final String TYPE_ID_VALUE = "type.id";
  private static final String TAG_ID_VALUE = "tags[%s].id";
  
  private final @NonNull HService hService;

  private final @NonNull TagsService tagsService;

  private final @NonNull HTypeService typeService;

  @Override
  public boolean supports(Class<?> clazz) {
    return HDTO.class.isAssignableFrom(clazz);
  }

  @Override
  public void validate(Object target, Errors errors) {
    HDTO dto = (HDTO) target;

    if (Objects.nonNull(dto.getTags()) && !dto.getTags().isEmpty()) {
      IntStream.range(0, dto.getTags().size())
      .forEach(index -> {
        TagsDTO tag = dto.getTags().get(index);
        validateExistingTag(tag, index, errors);
      });
    }
    
    Optional.of(dto)
      .map(HDTO::getType)
      .map(t -> typeService.getHType(t.getId()))
      .ifPresentOrElse(ht -> {},
        () -> registerError(errors, TYPE_ID_VALUE, com.jjo.h2.exception.Errors.NO_DATA_BY_ID_MSG, TYPE_ID_VALUE, dto.getType().getId()));
  }

  private void validateExistingTag(TagsDTO tag, int index, Errors errors) {
    Tags existingTag = tagsService.getTag(tag.getId());
    if (Objects.isNull(existingTag)) {
      String tagField = String.format(TAG_ID_VALUE, index);
      registerError(errors, tagField, com.jjo.h2.exception.Errors.NO_DATA_BY_ID_MSG, tagField, tag.getId());
    }
  }

  private void registerError(Errors errors, String field, com.jjo.h2.exception.Errors error, Object... args) {
    String code = String.format(error.getCode(), args);
    String message = String.format(error.getMessage(), args);
    errors.rejectValue(field, code, message);
  }
}
