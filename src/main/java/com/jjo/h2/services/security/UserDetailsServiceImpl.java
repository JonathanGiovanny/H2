package com.jjo.h2.services.security;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.jjo.h2.exception.Errors;
import com.jjo.h2.model.security.AccessData;
import com.jjo.h2.model.security.Privilege;
import com.jjo.h2.model.security.Role;
import com.jjo.h2.model.security.User;
import com.jjo.h2.repositories.security.PrivilegeRepository;
import com.jjo.h2.repositories.security.RoleRepository;
import com.jjo.h2.repositories.security.UserRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

  private final @NonNull UserRepository userRepo;

  private final @NonNull RoleRepository roleRepo;

  private final @NonNull PrivilegeRepository privRepo;

  public UserDetails loadUserById(Long id) {
    return userRepo.findById(id).map(this::buildUserDetails)
        .orElseThrow(() -> new UsernameNotFoundException(Errors.MISSING_USER.getMessage() + " with id: " + id));
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return userRepo.findByUsernameOrEmail(username, username, 2).map(this::buildUserDetails)
        .orElseThrow(() -> new UsernameNotFoundException(Errors.MISSING_USER.getMessage()));
  }

  /**
   * Generate UserDetails based on the User entity we have
   * 
   * @param user
   * @return
   */
  private UserDetails buildUserDetails(User user) {
    UserBuilder builder = org.springframework.security.core.userdetails.User.withUsername(user.getUsername());
    builder.password(user.getPassword());
    builder.authorities(getGrantedAuthorities(user.getRoles()));

    return builder.build();
  }

  /**
   * Create the authorities for the privileges list
   * 
   * @param roles
   * @return
   */
  private Set<GrantedAuthority> getGrantedAuthorities(Set<Role> roles) {
    return roles.stream().map(r -> Optional.ofNullable(r.getPrivileges()).orElse(Set.of())).flatMap(Set::stream).map(AccessData::getPrivilege)
        .map(Privilege::getName).distinct().map(SimpleGrantedAuthority::new).collect(Collectors.toSet());
  }
}
