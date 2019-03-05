package com.jjo.h2.dto;

import java.util.Set;

import com.googlecode.jmapper.annotations.JMap;
import com.jjo.h2.dto.util.BaseDTO;
import com.jjo.h2.model.Tags;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class HDTO extends BaseDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1031572710019580245L;

	@JMap
	private Long id;

	@JMap
	private String name;

	@JMap
	private String url;

	@JMap
	private String cover;

	@JMap
	private Long clicks;

	@JMap
	private Double score;

	@JMap
	private HTypeDTO type;

	@JMap
	private Set<Tags> tags;
}
