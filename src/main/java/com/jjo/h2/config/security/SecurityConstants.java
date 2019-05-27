package com.jjo.h2.config.security;

import com.jjo.h2.utils.Constants;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class SecurityConstants {

  public static final String AUTH_LOGIN_URL = Constants.APP_NAME + "/security/login";

  // Signing key for HS512 algorithm
  // You can use the page http://www.allkeysgenerator.com/ to generate all kinds of keys
  static final String JWT_SECRET = "n2r5u8x/A%D*G-KaPdSgVkYp3s6v9y$B&E(H+MbQeThWmZq4t7w!z%C*F-J@NcRf";

  // JWT token defaults
  static final String TOKEN_HEADER = "Authorization";
  static final String TOKEN_PREFIX = "Bearer ";
  static final String TOKEN_TYPE = "JWT";
  static final String TOKEN_ISSUER = "secure-api";
  static final String TOKEN_AUDIENCE = "secure-app";
}
