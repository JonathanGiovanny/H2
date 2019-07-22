package com.jjo.h2.controller.validator;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
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

    List<TagsDTO> tagsDto = Optional.ofNullable(dto)
      .map(HDTO::getTags)
      .map(Set::stream)
      .orElse(Stream.empty())
      .collect(Collectors.toList());
    for (int i = 0; i < tagsDto.size(); i++) {
      TagsDTO tag = tagsDto.get(i);
      validateExistingTag(tag, i, errors);
    }

    Optional.ofNullable(dto)
      .map(HDTO::getType)
      .map(t -> typeService.getHType(t.getId()))
      .ifPresentOrElse(ht -> {},
        () -> {
        String code = String.format(com.jjo.h2.exception.Errors.NO_DATA_BY_ID_MSG.getCode(), TYPE_ID_VALUE, dto.getType().getId());
        String message = String.format(com.jjo.h2.exception.Errors.NO_DATA_BY_ID_MSG.getMessage(), TYPE_ID_VALUE, dto.getType().getId());
        errors.rejectValue(TYPE_ID_VALUE, code, message);
      });
  }

  private void validateExistingTag(TagsDTO tag, int index, Errors errors) {
    Tags existingTag = tagsService.getTag(tag.getId());
    if (Objects.nonNull(existingTag)) {
      String tagField = String.format(TAG_ID_VALUE, index);
      String code = String.format(com.jjo.h2.exception.Errors.NO_DATA_BY_ID_MSG.getCode(), tagField, tag.getId());
      String message = String.format(com.jjo.h2.exception.Errors.NO_DATA_BY_ID_MSG.getMessage(), tagField, tag.getId());
      errors.rejectValue(tagField, code, message);
    }
  }
}
