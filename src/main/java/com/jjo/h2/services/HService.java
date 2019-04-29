package com.jjo.h2.services;

import java.util.List;
import org.springframework.data.domain.Pageable;
import com.jjo.h2.dto.HDTO;

public interface HService {

  /**
   * Get an specific H by its id
   * 
   * @param id
   * @return
   */
  public HDTO getH(Long id);

  /**
   * Save / Update the record
   * 
   * @param h
   * @return
   */
  public HDTO saveH(HDTO h);

  /**
   * Get all the H records based on the filter
   * 
   * @param filter
   * @param pageable
   * @return
   */
  public List<HDTO> findAll(HDTO filter, Pageable pageable);

  /**
   * Get all the H records
   * 
   * @param pageable
   * @return
   */
  public List<HDTO> findAll(Pageable pageable);

  /**
   * Delete a record based on the id
   * 
   * @param id
   * @throws HException
   */
  public void deleteH(Long id);

  /**
   * Increase number of Clicks on a record
   * 
   * @param id
   * @return
   */
  public HDTO increaseClick(Long id);
}
