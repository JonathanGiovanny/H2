package com.jjo.h2.dto.util;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class ErrorDTO extends BaseDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3646287721624949654L;

	private Integer code;

	private String errorMessage;

	private String errorDetail;
}
