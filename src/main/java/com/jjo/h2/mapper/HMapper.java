package com.jjo.h2.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import com.jjo.h2.dto.HDTO;
import com.jjo.h2.model.H;

@Mapper(componentModel = "spring")
public interface HMapper {

  @Mapping(target = "clicks", defaultValue = "0L")
  @Mapping(target = "score", defaultValue = "0.0D")
  H dtoToEntity(HDTO dto);

  HDTO entityToDTO(H entity);

  List<HDTO> entityToDTO(List<H> entity);
}
