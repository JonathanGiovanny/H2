package com.jjo.h2.services;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.jjo.h2.exception.Either;
import com.jjo.h2.model.H;
import com.jjo.h2.model.HHistory;
import com.jjo.h2.repositories.HRepository;
import com.jjo.h2.utils.Utils;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class HServiceImpl implements HService {

  private final @NonNull HRepository hRepo;

  private final @NonNull HHistoryService hHisService;

  private final @NonNull HTypeService htService;

  private final @NonNull TagsService tagsService;

  private Either<?, H> getH(Long id) {
    return Either.of(Utils.throwNotExistingElement(Arrays.asList("id", id)), hRepo.findById(id));
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
    getH(id).ifPresentOrThrow(eh -> copyExistingValuesThatShouldNotUpdate(eh, h));
    return saveH(h);
  }

  @Override
  public Page<H> findAll(Pageable pageable) {
    return findAll(new H(), pageable);
  }

  @Override
  public Page<H> findAll(H filter, Pageable pageable) {
    return hRepo.filter(filter, pageable);
  }

  @Override
  public void deleteH(Long id) {
    this.getH(id).ifPresentOrThrow(hRepo::delete);
  }

  @Override
  public H increaseClick(Long id) {
    H resultingH = getH(id)
        .peek(h -> h.setClicks(h.getClicks() + 1))
        .mapRight(hRepo::save)
        .getOrElse();

    HHistory hh = HHistory.builder().h(resultingH).date(LocalDateTime.now()).build();
    hHisService.save(hh);

    return resultingH;
  }

  private void copyExistingValuesThatShouldNotUpdate(H existingEntity, H updatingEntity) {
    updatingEntity.setClicks(existingEntity.getClicks());
  }
}
