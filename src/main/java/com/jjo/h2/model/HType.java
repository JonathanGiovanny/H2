package com.jjo.h2.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "H_TYPE")
public class HType implements Serializable {

	private static final long serialVersionUID = -2802154356808555113L;

	@Id
	@SequenceGenerator(name = "HT_SEQ_ID", sequenceName = "HT_SEQ_ID", allocationSize = 1)
	@GeneratedValue(generator = "HT_SEQ_ID")
	@Column(name = "HT_ID")
	private Integer id;

	@Column(name = "HT_NAME", nullable = false)
	private String name;
}
