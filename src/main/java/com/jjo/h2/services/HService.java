package com.jjo.h2.services;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.jjo.h2.dto.HDTO;
import com.jjo.h2.exception.HException;

public interface HService {

	/**
	 * Get an specific H by its id
	 * 
	 * @param id
	 * @return
	 */
	public HDTO getH(Long id) throws HException;

	/**
	 * Save / Update the record
	 * 
	 * @param h
	 * @return
	 */
	public boolean saveH(HDTO h) throws HException;

	/**
	 * Get all the H records based on the filter
	 * 
	 * @param filter
	 * @param pageable
	 * @return
	 */
	public List<HDTO> findAll(HDTO filter, Pageable pageable) throws HException;

	/**
	 * Get all the H records
	 * 
	 * @param pageable
	 * @return
	 */
	public List<HDTO> findAll(Pageable pageable) throws HException;

	/**
	 * Delete a record based on the id
	 * @param id
	 * @throws HException
	 */
	public void deleteH(Long id) throws HException;
	
	/**
	 * Increase number of Clicks on a record
	 * 
	 * @param id
	 * @return
	 */
	public boolean increaseClick(Long id) throws HException;
}
