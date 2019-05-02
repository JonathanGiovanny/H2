package com.jjo.h2.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jjo.h2.model.Tags;

public interface TagsRepository extends JpaRepository<Tags, Long> {

}
