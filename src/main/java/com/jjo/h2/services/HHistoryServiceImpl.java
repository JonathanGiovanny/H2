package com.jjo.h2.services;

import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.jjo.h2.model.HHistory;
import com.jjo.h2.repositories.HHistoryRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HHistoryServiceImpl implements HHistoryService {

  private final @NonNull HHistoryRepository hHistoryRepo;

  @Override
  public List<HHistory> getAll(Pageable pageable) {
    return this.hHistoryRepo.findAll(pageable).getContent();
  }

  @Override
  public HHistory save(HHistory hHistory) {
    return hHistoryRepo.save(hHistory);
  }
}
