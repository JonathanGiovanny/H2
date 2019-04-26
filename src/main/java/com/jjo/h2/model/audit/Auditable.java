package com.jjo.h2.model.audit;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.googlecode.jmapper.annotations.JMap;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
@MappedSuperclass // A mapped superclass has no separate table defined for it.
@EntityListeners(AuditingEntityListener.class)
public class Auditable implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 74601469141015187L;

	@JMap
	@CreatedDate
	@Column(name = "CREATION_DATE", updatable =  false)
	private LocalDateTime creationDate;

	@JMap
	@CreatedBy
	@Column(name = "CREATED_BY")
	private String createdBy;

	@JMap
	@LastModifiedDate
	@Column(name = "MODIFIED_DATE")
	private LocalDateTime modifiedDate;
}
