package com.jjo.h2.services;

import java.util.List;
import org.springframework.data.domain.Pageable;
import com.jjo.h2.dto.HHistoryDTO;

public interface HHistoryService {

  /**
   * Get all the HHistory based on the paged request
   * 
   * @param pageable
   * @return
   */
  List<HHistoryDTO> getAll(Pageable pageable);

  /**
   * Save the History record
   * 
   * @param hHistory
   * @return
   */
  HHistoryDTO save(HHistoryDTO hHistory);
}
