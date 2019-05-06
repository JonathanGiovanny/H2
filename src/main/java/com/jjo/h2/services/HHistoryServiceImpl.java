package com.jjo.h2.services;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.jjo.h2.dto.HHistoryDTO;
import com.jjo.h2.model.HHistory;
import com.jjo.h2.repositories.HHistoryRepository;
import com.jjo.h2.utils.MapperUtil;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HHistoryServiceImpl implements HHistoryService {

  private final @NonNull HHistoryRepository hHistoryRepo;

  private final @NonNull MapperUtil mapperUtil;

  @Override
  public List<HHistoryDTO> getAll(Pageable pageable) {
    return this.hHistoryRepo.findAll(pageable).getContent().stream().map(this::toDTO).collect(Collectors.toList());
  }

  @Override
  public HHistoryDTO save(HHistoryDTO hHistory) {
    return toDTO(hHistoryRepo.save(toEntity(hHistory)));
  }

  /**
   * Call the mapper and transform the object
   * 
   * @param dto
   * @return
   */
  private HHistory toEntity(HHistoryDTO dto) {
    return mapperUtil.getMapper(HHistory.class, HHistoryDTO.class).getDestination(dto);
  }

  /**
   * Call the mapper and transform the object
   * 
   * @param entity
   * @return
   */
  private HHistoryDTO toDTO(HHistory entity) {
    return mapperUtil.getMapper(HHistoryDTO.class, HHistory.class).getDestination(entity);
  }
}
