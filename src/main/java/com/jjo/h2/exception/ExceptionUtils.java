package com.jjo.h2.exception;

import com.jjo.h2.dto.util.ErrorDTO;

public class ExceptionUtils {

	/**
	 * Private constructor for static only methods
	 */
	private ExceptionUtils() {
		super();
	}

	/**
	 * Convert from {@link HException} to ErrorDTO to be send through the response
	 * 
	 * @param e
	 * @return
	 */
	public static ErrorDTO getErrorDTO(HException e) {
		ErrorDTO errorDTO = new ErrorDTO();
		errorDTO.setCode(e.getCode());
		errorDTO.setErrorMessage(e.getHMessage());
		errorDTO.setErrorMessage(e.getMessage());
		return errorDTO;
	}
}
