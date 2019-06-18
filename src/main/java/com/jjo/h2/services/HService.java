package com.jjo.h2.services;

import java.util.List;
import org.springframework.data.domain.Pageable;
import com.jjo.h2.exception.HError;
import com.jjo.h2.model.H;

public interface HService {

  H getH(Long id);

  H saveH(H h);

  /**
   * Modify an entity with out changing the tags or clicks
   * @param id
   * @param h
   * @return
   */
  H updateH(Long id, H h);

  /**
   * Get all the H records based on the filter
   * 
   * @param filter
   * @param pageable
   * @return
   */
  List<H> findAll(H filter, Pageable pageable);

  /**
   * Get all the H records
   * 
   * @param pageable
   * @return
   */
  List<H> findAll(Pageable pageable);

  /**
   * Delete a record based on the id
   * 
   * @param id
   * @throws HError
   */
  void deleteH(Long id);

  /**
   * Increase number of Clicks on a record
   * 
   * @param id
   * @return
   */
  H increaseClick(Long id);
}
