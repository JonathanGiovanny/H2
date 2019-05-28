package com.jjo.h2.controller.validator;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import com.jjo.h2.dto.HDTO;
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
    return HDTO.class.isAssignableFrom(clazz) || List.class.isAssignableFrom(clazz);
  }

  @Override
  public void validate(Object target, Errors errors) {
    HDTO dto = (HDTO) target;

    Optional.ofNullable(dto).map(HDTO::getTags).ifPresent(t -> t.forEach(tag -> tagsService.getTag(tag.getId())));
    Optional.ofNullable(dto).map(HDTO::getType).ifPresent(t -> typeService.getHType(t.getId()));

    dto.setClicks(Optional.ofNullable(dto).map(HDTO::getClicks).orElse(0L));
    dto.setScore(Optional.ofNullable(dto).map(HDTO::getScore).orElse(0.0));
  }
}
