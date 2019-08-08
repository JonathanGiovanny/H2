package com.jjo.h2.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import com.jjo.h2.exception.Errors;
import com.jjo.h2.exception.HException;
import com.jjo.h2.model.H;
import com.jjo.h2.model.HHistory;
import com.jjo.h2.repositories.HRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class HServiceImpl implements HService {

  private static final String URL = "url";
  
  private final @NonNull HRepository hRepo;

  private final @NonNull HHistoryService hHisService;

  private final @NonNull HTypeService htService;

  private final @NonNull TagsService tagsService;

  private H getH(Long id) {
    return hRepo.findById(id).orElseThrow(() -> new HException(Errors.NO_DATA, Pair.of("id", id).toString()));
  }

  @Override
  public Boolean isUrlAvailable(Long id, String url) {
    Optional<H> foundH = hRepo.findByUrlIgnoreCase(url);
    return !foundH.map(H::getId).filter(foundId -> !foundId.equals(id)).isPresent();
  }

  public H saveH(H h) {
    return hRepo.save(h);
  }

  @Override
  public H updateH(Long id, H h) {
    h.setId(id);
    Optional<H> existingH = hRepo.findById(id);
    existingH.ifPresent(eh -> copyExistingValuesThatShouldNotUpdate(eh, h));
    return saveH(h);
  }

  @Override
  public List<H> findAll(Pageable pageable) {
    return findAll(new H(), pageable);
  }

  @Override
  public List<H> findAll(H filter, Pageable pageable) {
    return hRepo.filter(filter, pageable);
  }

  @Override
  public void deleteH(Long id) {
    hRepo.delete(this.getH(id));
  }

  @Override
  public H increaseClick(Long id) {
    H h = getH(id);
    h.setClicks(h.getClicks() + 1);
    H resultingH = hRepo.save(h);

    HHistory hh = HHistory.builder().h(resultingH).date(LocalDateTime.now()).build();
    hHisService.save(hh);

    return resultingH;
  }

  private void copyExistingValuesThatShouldNotUpdate(H existingEntity, H updatingEntity) {
    updatingEntity.setClicks(existingEntity.getClicks());
  }
}
