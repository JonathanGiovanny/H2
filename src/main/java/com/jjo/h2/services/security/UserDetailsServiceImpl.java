package com.jjo.h2.services.security;

import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.jjo.h2.exception.ErrorConstants;
import com.jjo.h2.model.security.Role;
import com.jjo.h2.model.security.User;
import com.jjo.h2.repositories.security.UserRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

  private UserRepository userRepo;

  public UserDetails loadUserById(Long id) {
    return userRepo.findById(id).map(this::buildUserDetails)
        .orElseThrow(() -> new UsernameNotFoundException(ErrorConstants.MISSING_USER + " with id: " + id));
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return userRepo.findByUsername(username).or(() -> userRepo.findByEmail(username)).map(this::buildUserDetails)
        .orElseThrow(() -> new UsernameNotFoundException(ErrorConstants.MISSING_USER));
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
    return roles.stream().flatMap(r -> r.getPrivileges().stream()).map(p -> p.getPrivilege().getName()).distinct().map(SimpleGrantedAuthority::new)
        .collect(Collectors.toSet());
  }
}
