package com.jjo.h2.services;

import java.util.List;
import org.springframework.data.domain.Pageable;
import com.jjo.h2.model.H;

public interface HService {

  H saveH(H h);

  H updateH(Long id, H h);

  /**
   * Get all the H records based on the filter
   * 
   * @param filter
   * @param pageable
   * @return
   */
  List<H> findAll(H filter, Pageable pageable);

  List<H> findAll(Pageable pageable);

  void deleteH(Long id);

  H increaseClick(Long id);

  Boolean isUrlAvailable(String url);
}
