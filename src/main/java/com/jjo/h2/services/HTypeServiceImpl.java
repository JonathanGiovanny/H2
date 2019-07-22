package com.jjo.h2.services;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
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
    return hTypeRepo.findById(id).orElse(null);
  }

  @Override
  public List<HType> findAll(Pageable pageable) {
    return hTypeRepo.findAll(pageable).getContent();
  }

  @Override
  public Boolean isNameAvailable(String name) {
    return Objects.nonNull(findByName(name));
  }
  
  @Override
  public HType findByName(String name) {
    return hTypeRepo.findByNameIgnoreCase(name).get();
  }

  @Override
  public List<HType> findByNameLike(String name, Pageable pageable) {
    return hTypeRepo.findByNameLike(name, pageable);
  }

  @Override
  public HType saveHType(HType hType) {
    return Optional.ofNullable(hType)
        .filter(entity -> validateHTypeNameUnique(entity.getId(), entity.getName()))
        .map(hTypeRepo::save)
        .get();
  }

  @Override
  public void deleteHType(Integer id) {
    hTypeRepo.deleteById(id);
  }

  private boolean validateHTypeNameUnique(Integer id, String name) {
    Optional<HType> existingH = hTypeRepo.findByNameIgnoreCase(name);
    if (existingH.isPresent() && !existingH.get().getId().equals(id)) {
      throw new HException(Errors.FIELD_SHOULD_UNIQUE, "name");
    }
    return true;
  }
}
