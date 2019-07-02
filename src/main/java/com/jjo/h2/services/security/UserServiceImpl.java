package com.jjo.h2.services.security;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.function.BiPredicate;
import java.util.function.UnaryOperator;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.jjo.h2.config.DatasourceNeo4j;
import com.jjo.h2.dto.security.UserDTO;
import com.jjo.h2.model.security.RolesEnum;
import com.jjo.h2.model.security.User;
import com.jjo.h2.repositories.security.UserRepository;
import com.jjo.h2.utils.Utils;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(value = DatasourceNeo4j.TRANSACTION_MANAGER)
public class UserServiceImpl implements UserService {

  private final @NonNull UserRepository userRepo;

  private final @NonNull RolesService roleService;

  private final @NonNull PasswordEncoder passEncoder;

  @Override
  public Boolean availableUsernameOrEmail(String username, String email) {
    return userRepo.findByUsernameOrEmail(username, email, 1).isEmpty();
  }

  @Override
  public Long registerUser(User user) {
    user.setPassword(passEncoder.encode(user.getPassword()));
    user.setRoles(Set.of(roleService.getRoleByName(RolesEnum.ROLE_USER.name())));

    return userRepo.save(user).getId();
  }

  @Override
  public List<User> getUsers(Pageable pageable) {
    return userRepo.findAll(pageable).getContent();
  }

  @Override
  public User updateUser(Long id, User user) {
    User entity = userRepo.findById(id).orElseThrow();
    //copyDTO(user, entity);
    return userRepo.save(entity);
  }

  private User updatePassword(User user) {
    User entity = userRepo.findById(user.getId()).orElseThrow();
    BiPredicate<String, String> passwordsNotMatch = (String dtoP, String eP) -> !passEncoder.matches(user.getPassword(), entity.getPassword());

    UnaryOperator<String> f = str -> {
      entity.setPasswordDate(LocalDate.now());
      return null;
    };

    entity.setPassword(Utils.isNotNullOr(user.getPassword(), entity.getPassword(), passwordsNotMatch, f));
    return null;
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
}
