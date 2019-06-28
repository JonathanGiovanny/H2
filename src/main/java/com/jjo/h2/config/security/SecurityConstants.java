package com.jjo.h2.config.security;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SecurityConstants {

  static final String WWW_AUTHENTICATE = "WWW-Authenticate";

  public static final String SECURITY_PATH = "/security";
  public static final String AUTH_LOGIN_URL = SECURITY_PATH + "/login";
  public static final String TOKEN_PREFIX = "JWT ";
  public static final String TOKEN_HEADER = "Authorization";

  public static final String READ_H = "hasPermission(#this, 'READ_H')";
  public static final String MODIFY_H = "hasPermission(#this, 'MODIFY_H')";
  public static final String DELETE_H = "hasPermission(#this, 'DELETE_H')";
  public static final String FILTER_H = "hasPermission(#this, 'FILTER_H')";
  public static final String READ_TAGS = "hasPermission(#this, 'READ_TAGS')";
  public static final String MODIFY_TAGS = "hasPermission(#this, 'MODIFY_TAGS')";
  public static final String DELETE_TAGS = "hasPermission(#this, 'DELETE_TAGS')";
  public static final String READ_TYPES = "hasPermission(#this, 'READ_TYPES')";
  public static final String MODIFY_TYPES = "hasPermission(#this, 'MODIFY_TYPES')";
  public static final String DELETE_TYPES = "hasPermission(#this, 'DELETE_TYPES')";
  
  public static final String READ_USERS = "hasPermission(#this, 'READ_USERS')";
  public static final String READ_ROLES = "hasPermission(#this, 'READ_ROLES')";
  public static final String MODIFY_ROLES = "hasPermission(#this, 'MODIFY_ROLES')";
}
