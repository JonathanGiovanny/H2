package com.jjo.h2.exception;

public class ErrorConstants {

  public static final String GENERIC_ERROR_MSG = "Se ha presentado un problema";
  public static final String NO_DATA_MSG = "No se han encontrado datos";
  public static final String NO_DATA_BY_ID_MSG = "El elemento con id %s no existe";
  public static final String MISSING_FIELD = "El campo '%s' es obligatorio";
  public static final String MISMATCH_FIELD = "El campo '%s' puede contener un valor no esperado";
  public static final String MISMATCH_INPUT = "El Request contiene errores, por favor verifica";
  public static final String INVALID_DATE_FORMAT = "El formato de la fecha no es válido";
  public static final String FIELD_SHOULD_UNIQUE = "El campo '%s' debe ser único, ya existe un registro con este valor";
  public static final String BAD_QUERY = "Se presento un error en la transacción de los datos";

  /**
   * Private constructor for constants class
   */
  private ErrorConstants() {
    super();
  }
}
