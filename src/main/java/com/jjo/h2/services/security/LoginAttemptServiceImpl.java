package com.jjo.h2.services.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class LoginAttemptServiceImpl implements LoginAttempService {

  @Value("${h.config.loginAttempts}")
  private int loginAttempts;

  
}
