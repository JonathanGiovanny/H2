package com.jjo.h2.utils;

import org.springframework.http.HttpStatus;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class HException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5447371364481876151L;

	private int code;

	private String hMessage;

	public HttpStatus getStatusCode() {
		HttpStatus status = null;

		switch(code) {
		case ErrorConstants.NO_DATA_CODE:
			status = HttpStatus.NO_CONTENT;
		default:
			status = HttpStatus.CONFLICT;
		}
		return status;
	}
}
