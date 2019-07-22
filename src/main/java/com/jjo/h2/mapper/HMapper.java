package com.jjo.h2.mapper;

import java.util.List;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import com.jjo.h2.dto.HDTO;
import com.jjo.h2.model.H;

@Mapper(componentModel = "spring")
public interface HMapper {

  @Mapping(target = "clicks", defaultValue = "0L", ignore = true)
  @Mapping(target = "score", defaultValue = "0.0D")
  @Mapping(target = "modifiedDate", ignore = true)
  H dtoToEntity(HDTO dto);

  HDTO entityToDTO(H entity);

  @InheritConfiguration
  List<HDTO> entityToDTO(List<H> entity);
}
