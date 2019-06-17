package com.jjo.h2.services.security.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import com.jjo.h2.dto.security.SingUpDTO;
import com.jjo.h2.dto.security.UserDTO;
import com.jjo.h2.model.security.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

  UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

  @Mapping(target = "passwordDate", defaultValue = "java.time.LocalDate.now()")
  User singUpToUser(SingUpDTO singUp);

  User dtoToEntity(UserDTO userDto);

  UserDTO entityToDto(User user);
}
