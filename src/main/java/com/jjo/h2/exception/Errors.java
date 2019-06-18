package com.jjo.h2.exception;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum Errors {

  GENERIC_ERROR("general.error", "Se ha presentado un problema"),
  NO_DATA("no.data", "No se han encontrado datos"),
  NO_DATA_BY_ID_MSG("no.data.by:%s", "El elemento con id %s no existe"),
  MISSING_FIELD("missing.field:%s", "El campo '%s' es obligatorio"),
  MISMATCH_FIELD("mismatch.%s.field", "El campo '%s' puede contener un formato o tipo de dato no esperado"),
  MISMATCH_INPUT("mismatch.request", "El Request contiene errores"),
  INVALID_DATE_FORMAT("invalid.date", "El formato de la fecha no es válido"),
  FIELD_SHOULD_UNIQUE("invalid:%s", "El campo '%s' debe ser único, ya existe un registro con este valor"),
  BAD_QUERY("invalid.database.process", "Se presento un error en la transacción de los datos"),

  MISSING_USER("missing.user", "Usuario no encontrado"),
  EXISTING_USER("existing.user", "El nombre de usuario o correo ya existe"),

  UNAUTHORIZED_REQUEST("unauthorized.request", "Se ha intentado acceder a un recurso sin la debida autorización"),
  EXPIRED_TOKEN("token.expired", "Su sesión ha expirado");

  private String code;
  private String message;
}
