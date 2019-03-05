package com.jjo.h2.services;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.jjo.h2.model.HHistory;

public interface HHistoryService {

	/**
	 * Get all the HHistory based on the paged request
	 * 
	 * @param pageable
	 * @return
	 */
	public List<HHistory> getAll(Pageable pageable);
}
