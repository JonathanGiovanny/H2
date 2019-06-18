package com.jjo.h2.services;

import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.jjo.h2.exception.Errors;
import com.jjo.h2.exception.HException;
import com.jjo.h2.model.HType;
import com.jjo.h2.repositories.HTypeRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HTypeServiceImpl implements HTypeService {

  private final @NonNull HTypeRepository hTypeRepo;

  @Override
  public HType getHType(Integer id) {
    return hTypeRepo.getOne(id);
  }

  @Override
  public List<HType> findAll(Pageable pageable) {
    return hTypeRepo.findAll(pageable).getContent();
  }

  @Override
  public HType findByName(String name) {
    return hTypeRepo.findByName(name);
  }

  @Override
  public List<HType> findByNameLike(String name, Pageable pageable) {
    return hTypeRepo.findByNameLike(name, pageable);
  }

  @Override
  public HType saveHType(HType hType) {
    HType existingName = findByName(hType.getName());
    Predicate<HType> hasExistingValue = ht -> Objects.nonNull(existingName) && Objects.isNull(ht.getId());
    Predicate<HType> hasExistingValueAndOtherId =
        ht -> Objects.nonNull(existingName) && Objects.nonNull(ht.getId()) && !existingName.getId().equals(ht.getId());

    if (hasExistingValue.or(hasExistingValueAndOtherId).test(hType)) {
      throw new HException(Errors.FIELD_SHOULD_UNIQUE, "name");
    }

    return hTypeRepo.save(hType);
  }

  @Override
  public void deleteHType(Integer id) {
    hTypeRepo.deleteById(id);
  }
}
