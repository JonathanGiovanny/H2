package com.jjo.h2.config.security;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SecurityConstants {

  static final String WWW_AUTHENTICATE = "WWW-Authenticate";

  public static final String SECURITY_PATH = "/H/security";
  public static final String AUTH_LOGIN_URL = SECURITY_PATH + "/login";
  public static final String TOKEN_PREFIX = "JWT ";
  public static final String TOKEN_HEADER = "Authorization";
}
