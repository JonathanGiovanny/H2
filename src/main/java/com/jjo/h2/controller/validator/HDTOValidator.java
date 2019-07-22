package com.jjo.h2.controller.validator;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import com.jjo.h2.dto.HDTO;
import com.jjo.h2.dto.TagsDTO;
import com.jjo.h2.services.HService;
import com.jjo.h2.services.HTypeService;
import com.jjo.h2.services.TagsService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class HDTOValidator implements Validator {

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

    Stream<TagsDTO> tagsDto = Optional.ofNullable(dto).map(HDTO::getTags).map(Set::stream).orElse(Stream.empty());
    tagsDto.map(tag -> tagsService.getTag(tag.getId())).collect(Collectors.toList());

    Optional.ofNullable(dto)
      .map(HDTO::getType).map(t -> typeService.getHType(t.getId()))
      .ifPresentOrElse(ht -> {
        String code = String.format(com.jjo.h2.exception.Errors.NO_DATA_BY_ID_MSG.getCode(), ht.getId());
        String message = String.format(com.jjo.h2.exception.Errors.NO_DATA_BY_ID_MSG.getMessage(), ht.getId());
        errors.reject(code, message);
      }, () -> {});
  }
}
