package com.jjo.h2.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import com.jjo.h2.model.H;

public interface HRepository extends JpaRepository<H, Long>, JpaSpecificationExecutor<H> {
}
