package com.jjo.h2.config.security;

import com.jjo.h2.utils.Constants;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SecurityConstants {

  static final String SECURITY_PATH = "/" + Constants.APP_NAME + "/security/";
  static final String WWW_AUTHENTICATE = "WWW-Authenticate";

  public static final String AUTH_LOGIN_URL = SECURITY_PATH + "login";
  public static final String TOKEN_PREFIX = "JWT ";
  public static final String TOKEN_HEADER = "Authorization";
}
