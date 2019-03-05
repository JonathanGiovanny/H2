package com.jjo.h2.dto;

import com.googlecode.jmapper.annotations.JMap;
import com.jjo.h2.dto.util.BaseDTO;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class HTypeDTO extends BaseDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4765661508331347452L;

	@JMap
	private Integer id;

	@JMap
	private String name;
}
