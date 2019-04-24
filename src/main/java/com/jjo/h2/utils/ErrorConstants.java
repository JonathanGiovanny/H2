package com.jjo.h2.utils;

public class ErrorConstants {

	public static final int GENERIC_ERROR_CODE = 0;
	public static final int EXISTING_USER_CODE = 1;
	public static final int NO_DATA_CODE = 2;

	public static final String GENERIC_ERROR_MSG = "Se ha presentado un problema";
	public static final String EXISTING_USER_MSG = "El nombre de usuario ya existe";
	public static final String NO_DATA_MSG = "No se han encontrado datos";

	/**
	 * Private constructor for constants class
	 */
	private ErrorConstants() {
		super();
	}
}
