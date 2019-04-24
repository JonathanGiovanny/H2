package com.jjo.h2.model.security;

import java.sql.Blob;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.googlecode.jmapper.annotations.JMap;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@NodeEntity
@EntityListeners(AuditingEntityListener.class)
public class User {

	@Id
	@GeneratedValue
	@JMap
	private Long id;

	@JMap
	private String username;

	@JMap
	private String password;

	@JMap
	private String email;

	@JMap
	@CreatedDate
	private LocalDateTime createdDate;

	@JMap
	private LocalDate passwordDate;

	@JMap
	private Blob profilePic;

	@JMap
	@Relationship(type = "HAS")
	private Set<Role> roles;
}
