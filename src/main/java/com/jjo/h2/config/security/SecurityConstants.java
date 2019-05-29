package com.jjo.h2.config.security;

import com.jjo.h2.utils.Constants;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SecurityConstants {

  public static final String AUTH_LOGIN_URL = Constants.APP_NAME + "/security/login";
  public static final String TOKEN_PREFIX = "Bearer ";
  public static final String TOKEN_HEADER = "Authorization";
}
