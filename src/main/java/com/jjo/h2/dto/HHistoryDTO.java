package com.jjo.h2.dto;

import java.time.LocalDateTime;

import com.googlecode.jmapper.annotations.JMap;
import com.jjo.h2.dto.util.BaseDTO;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class HHistoryDTO extends BaseDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6289274275168849632L;

	@JMap
	private Long id;

	@JMap
	private HDTO h;

	@JMap
	private LocalDateTime date;
}
