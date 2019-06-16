package com.jjo.h2.services.security;

import java.time.LocalDate;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.jjo.h2.config.DatasourceNeo4j;
import com.jjo.h2.dto.security.SingUpDTO;
import com.jjo.h2.dto.security.UserDTO;
import com.jjo.h2.model.security.User;
import com.jjo.h2.repositories.security.RoleRepository;
import com.jjo.h2.repositories.security.UserRepository;
import com.jjo.h2.utils.MapperUtil;
import com.jjo.h2.utils.Utils;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(value = DatasourceNeo4j.TRANSACTION_MANAGER)
public class UserServiceImpl implements UserService {

  private final UserRepository userRepo;
  
  private final RoleRepository roleRepo;

  private final PasswordEncoder passEncoder;

  private final MapperUtil mapperUtil;

  @Override
  public Boolean availableUsernameOrEmail(String usernameOrEmail) {
    return userRepo.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail).isEmpty();
  }

  @Override
  public Long registerUser(SingUpDTO user) {
    UserDTO userDto = new UserDTO();
    userDto.setUsername(user.getUsername());
    userDto.setPassword(passEncoder.encode(user.getPassword()));
    userDto.setPasswordDate(LocalDate.now());
    userDto.setEmail(user.getEmail());
    userDto.setProfilePic(user.getProfilePic());

    User entity = toEntity(userDto);
    entity.setRoles(roleRepo.findByName("ROLE_ADMIN"));
    userRepo.save(entity);
    return entity.getId();
  }

  @Override
  public List<UserDTO> getUsers(Pageable pageable) {
    return userRepo.findAll(pageable).getContent().stream().map(this::toDTO).collect(Collectors.toList());
  }

  @Override
  public UserDTO updateUser(Long id, UserDTO user) {
    User entity = userRepo.findById(id).orElseThrow();
    copyDTO(user, entity);
    return toDTO(userRepo.save(entity));
  }

  public boolean updatePassword(UserDTO user) {
    User entity = userRepo.findById(user.getId()).orElseThrow();
    BiPredicate<String, String> passwordsNotMatch = (String dtoP, String eP) -> !passEncoder.matches(user.getPassword(), entity.getPassword());

    UnaryOperator<String> f = str -> {
      entity.setPasswordDate(LocalDate.now());
      return str;
    };

    entity.setPassword(Utils.isNotNullOr(user.getPassword(), entity.getPassword(), passwordsNotMatch, f));
    return true;
  }

  /**
   * Copy the data from the dto to the entity
   * 
   * @param dto
   * @param entity
   * @return
   */
  private User copyDTO(UserDTO dto, User entity) {
    entity.setEmail(Utils.isNotNullOr(dto.getEmail(), entity.getEmail()));
    entity.setStatus(Utils.isNotNullOr(dto.getStatus(), entity.getStatus()));
    entity.setProfilePic(Utils.isNotNullOr(dto.getProfilePic(), entity.getProfilePic()));
    if (dto.getRoles() != null && entity.getRoles().size() == dto.getRoles().size() && entity.getRoles().containsAll(dto.getRoles())) {
      entity.setRoles(dto.getRoles());
    }
    return entity;
  }

  /**
   * Call the mapper and transform the object
   * 
   * @param dto
   * @return
   */
  private User toEntity(UserDTO dto) {
    return mapperUtil.getMapper(User.class, UserDTO.class).getDestination(dto);
  }

  /**
   * Call the mapper and transform the object
   * 
   * @param entity
   * @return
   */
  private UserDTO toDTO(User entity) {
    return mapperUtil.getMapper(UserDTO.class, User.class).getDestination(entity);
  }
}
