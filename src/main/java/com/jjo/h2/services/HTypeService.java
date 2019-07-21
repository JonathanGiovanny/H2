package com.jjo.h2.services;

import java.util.List;
import org.springframework.data.domain.Pageable;
import com.jjo.h2.model.HType;

public interface HTypeService {

  HType getHType(Integer id);

  List<HType> findAll(Pageable pageable);

  HType findByName(String name);

  List<HType> findByNameLike(String name, Pageable pageable);

  HType saveHType(HType hType);

  void deleteHType(Integer id);

  Boolean isNameAvailable(String name);
}
