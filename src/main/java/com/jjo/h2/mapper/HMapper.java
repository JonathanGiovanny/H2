package com.jjo.h2.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import com.jjo.h2.dto.HDTO;
import com.jjo.h2.model.H;

@Mapper(componentModel = "spring")
public interface HMapper {

  H dtoToEntity(HDTO dto);

  HDTO entityToDTO(H entity);

  List<HDTO> entityToDTO(List<H> entity);
}
