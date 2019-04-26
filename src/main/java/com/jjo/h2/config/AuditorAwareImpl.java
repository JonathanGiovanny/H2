package com.jjo.h2.config;

import java.util.Optional;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuditorAwareImpl {

  @Bean
  public AuditorAware<String> auditorAware() {
    return new AuditorAwareImplInner();
  }

  class AuditorAwareImplInner implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
      return Optional.ofNullable(SecurityContextHolder.getContext())
          .map(SecurityContext::getAuthentication).filter(Authentication::isAuthenticated)
          .map(Authentication::getName);
    }
  }
}
