package com.jjo.h2.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.jjo.h2.model.HHistory;
import com.jjo.h2.repositories.HHistoryRepository;

@Service
public class HHistoryServiceImpl implements HHistoryService {

	@Autowired
	private HHistoryRepository hHistoryRepo;

	@Override
	public List<HHistory> getAll(Pageable pageable) {
		return this.hHistoryRepo.findAll(pageable).getContent();
	}
}
