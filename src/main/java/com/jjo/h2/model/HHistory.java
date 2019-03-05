package com.jjo.h2.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.googlecode.jmapper.annotations.JMap;

import lombok.Data;

@Data
@Entity
@Table(name = "H_HISTORY")
public class HHistory implements Serializable {

	private static final long serialVersionUID = -8462244913061213684L;

	@Id
	@JMap
	@SequenceGenerator(name = "HH_SEQ_ID", sequenceName = "HH_SEQ_ID", allocationSize = 1)
	@GeneratedValue(generator = "HH_SEQ_ID")
	@Column(name = "HH_ID")
	private Long id;

	@JMap
	@ManyToOne
	@JoinColumn(name = "HH_H_ID", nullable = false)
	private H h;

	@JMap
	@Column(name = "HH_DATE", nullable = false, updatable = false)
	private LocalDateTime date;
}
