package com.jjo.h2.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.jjo.h2.dto.HDTO;
import com.jjo.h2.model.H;
import com.jjo.h2.model.HHistory;
import com.jjo.h2.repositories.HHistoryRepository;
import com.jjo.h2.repositories.HRepository;
import com.jjo.h2.utils.HException;
import com.jjo.h2.utils.MapperUtil;

@Service
public class HServiceImpl implements HService {

	@Autowired
	private HRepository hRepo;

	@Autowired
	private HHistoryRepository hHisRepo;

	@Autowired
	private MapperUtil mapperUtil;

	public HDTO getH(Long id) throws HException {
		return hRepo.findById(id).map(h -> toDTO(h)).orElse(null);
	}

	public boolean saveH(HDTO h) throws HException {
		return hRepo.save(toEntity(h)) != null;
	}

	public List<HDTO> findAll(Pageable pageable) throws HException {
		return findAll(new HDTO(), pageable);
	}

	public List<HDTO> findAll(HDTO filter, Pageable pageable) throws HException {
		return hRepo.findAll(Example.of(toEntity(filter)), pageable).getContent().stream()
				.map(h -> toDTO(h)).collect(Collectors.toList());
	}

	public void deleteH(Long id) throws HException {
		hRepo.delete(toEntity(this.getH(id)));
	}

	public boolean increaseClick(Long id) throws HException {
		H h = hRepo.getOne(id);

		HHistory hh = new HHistory();
		hh.setH(h);
		hh.setDate(LocalDateTime.now());
		hHisRepo.save(hh);

		h.setClicks(h.getClicks() + 1);
		hRepo.save(h);
		return true;
	}

	/**
	 * Call the mapper and transform the object
	 * 
	 * @param dto
	 * @return
	 */
	private H toEntity(HDTO dto) {
		return mapperUtil.getMapper(H.class, HDTO.class).getDestination(dto);
	}

	/**
	 * Call the mapper and transform the object
	 * 
	 * @param entity
	 * @return
	 */
	private HDTO toDTO(H entity) {
		return mapperUtil.getMapper(HDTO.class, H.class).getDestination(entity);
	}
}
