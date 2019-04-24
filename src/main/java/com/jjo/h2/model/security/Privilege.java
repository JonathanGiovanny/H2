package com.jjo.h2.model.security;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.neo4j.ogm.annotation.NodeEntity;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@NodeEntity
public class Privilege {

	@Id
	@GeneratedValue
	private Long id;

	private String name;

	private String url;

	private String icon;
}
