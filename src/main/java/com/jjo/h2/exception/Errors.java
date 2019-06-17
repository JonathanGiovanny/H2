package com.jjo.h2.exception;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum Errors {

  GENERIC_ERROR("general.error", "Se ha presentado un problema"),
  NO_DATA("no.data", "No se han encontrado datos"),
  NO_DATA_BY_ID_MSG("no.data.by.%s", "El elemento con id %s no existe"),
  MISSING_FIELD("missing.%s.field", "El campo '%s' es obligatorio"),
  MISMATCH_FIELD("mismatch.%s.field", "El campo '%s' puede contener un formato o tipo de dato no esperado"),
  MISMATCH_INPUT("", "El Request contiene errores, por favor verifica"),
  INVALID_DATE_FORMAT("", "El formato de la fecha no es válido"),
  FIELD_SHOULD_UNIQUE("", "El campo '%s' debe ser único, ya existe un registro con este valor"),
  BAD_QUERY("", "Se presento un error en la transacción de los datos"),
  MISSING_USER("", "Usuario no encontrado"),
  EXISTING_USER("", "El nombre de usuario ya existe"),

  UNAUTHORIZED_REQUEST("", "Se ha intentado acceder a un recurso sin la debida autorización, intente nuevamente"),
  EXPIRED_TOKEN("", "Su sesión ha expirado");
  
  private String code;
  private String message;
}
