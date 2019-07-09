package com.jjo.h2.exception;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum Errors {

  GENERIC_ERROR("general.error", "There has been a problem"),
  NO_DATA("no.data", "Not data found"),
  NO_DATA_BY_ID_MSG("no.data.by:%s", "The element with id %s does not exist"),
  MISSING_FIELD("missing.field:%s", "The field '%s' is required"),
  MISMATCH_FIELD("mismatch.%s.field", "The field '%s' can contain an unexpected format or data type"),
  MISMATCH_INPUT("mismatch.request", "The request contain errors"),
  INVALID_DATE_FORMAT("invalid.date", "The date format is not valid"),
  FIELD_SHOULD_UNIQUE("invalid:%s", "The field '%s', must be unique, it already exists a record with that value"),
  BAD_QUERY("invalid.database.process", "There has been an error with the transaction"),

  MISSING_USER("missing.user", "User not found"),
  EXISTING_USER("existing.user", "The username or email is already in use"),
  SAME_PASSWORD("password.same", "The password can not be the current"),
  BANNED_IP("banned.ip", "The ip %s has been banned for multiple login attempts"),
  LOGIN_FAILED("login.failed", "Login failed"),

  UNAUTHORIZED_REQUEST("unauthorized.request", "Cannot access to this resource without authorization"),
  EXPIRED_TOKEN("token.expired", "Session has expired");

  private String code;
  private String message;
}
