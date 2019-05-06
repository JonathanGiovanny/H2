package com.jjo.h2.exception;

public class ErrorConstants {

  public static final String GENERIC_ERROR_MSG = "Se ha presentado un problema";
  public static final String NO_DATA_MSG = "No se han encontrado datos";
  public static final String NO_DATA_BY_ID_MSG = "El elemento con id %s no existe";
  public static final String MISSING_FIELD = "El campo %s requiere un valor obligatorio";
  public static final String MISMATCH_FIELD = "El campo %s puede contener un valor inesperado";
  public static final String MISMATCH_INPUT = "El Request contiene errores, por favor verifica";

  /**
   * Private constructor for constants class
   */
  private ErrorConstants() {
    super();
  }
}
